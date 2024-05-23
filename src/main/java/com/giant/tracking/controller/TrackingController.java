package com.giant.tracking.controller;

import com.giant.tracking.domain.carrier.CarrierService;
import com.giant.tracking.dto.TrackingCjDto;
import com.giant.tracking.dto.TrackingDetailDto;
import com.giant.tracking.entity.CarrierInvoice;
import com.giant.tracking.util.Fn;
import com.giant.tracking.util.RestClientService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * packageName    : com.giant.tracking.controller
 * fileName       : TrackingController
 * author         : akfur
 * date           : 2024-03-29
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-03-29         akfur
 */
@Controller
@RequiredArgsConstructor
public class TrackingController {

    private final RestClientService restClientService;
    private final CarrierService carrierService;

    @GetMapping("/tracking")
    public String trackingPage() {
        return "tracking";
    }

    @GetMapping("/test")
    public String test() {
        Map m = new HashMap();
        if(true) throw new NullPointerException();
        return "";
    }

    @GetMapping("/trackingInfo")
    public String trackingInfo(Model model, @RequestParam("hwbNo") String hwbNo) {
        if(hwbNo.contains("BIGC")) {
            CarrierInvoice carrierInvoice = carrierService.findByNumber(hwbNo);
            hwbNo = carrierInvoice.getNumber();
        }

        if(hwbNo.startsWith("5")) {
            try {
                Document doc = Jsoup.connect("http://nexs.cjgls.com/web/info.jsp?slipno=" + hwbNo).get();
                Objects.requireNonNull(doc);

                Elements table = doc.select("table:eq(8)");
                Objects.requireNonNull(table.isEmpty());// Select the 5th table using CSS selector

                List<TrackingCjDto> trackingCjDtos = new ArrayList<>();
                for (Element row : table.select("tr")) {
                    Elements cells = row.select("td");
                    if (cells.size() >= 5) {
                        TrackingCjDto trackingCjDto = TrackingCjDto.builder()
                                .date(cells.get(0).text())
                                .time(cells.get(1).text())
                                .Area(cells.get(3).text())
                                .status(cells.get(5).text()).build();

                        trackingCjDtos.add(trackingCjDto);
                    }
                }

                String endStatus = "1";

                if(!trackingCjDtos.get(0).getArea().equals("Hub") && !trackingCjDtos.get(0).getArea().contains("HUB")) {
                    if(trackingCjDtos.get(0).getStatus().equals("간선상차")) endStatus = "1";
                    if(trackingCjDtos.get(0).getStatus().equals("집화처리")) endStatus = "2";
                    if(trackingCjDtos.get(0).getStatus().equals("배달출발")) endStatus = "7";
                    if(trackingCjDtos.get(0).getStatus().equals("배달완료")) endStatus = "8";
                }

                if(trackingCjDtos.get(0).getArea().contains("HUB")) {
                    if(trackingCjDtos.get(0).getStatus().equals("간선하차")) endStatus = "3";
                    if(trackingCjDtos.get(0).getStatus().equals("간선상차")) endStatus = "3";
                }

                if(trackingCjDtos.get(0).getArea().contains("Hub")) {
                    if(trackingCjDtos.get(0).getStatus().equals("간선하차")) endStatus = "4";
                    if(trackingCjDtos.get(0).getStatus().equals("간선상차")) endStatus = "4";
                }

                model.addAttribute("hwbNo", hwbNo);
                model.addAttribute("endStatus", endStatus);
                model.addAttribute("trackingCjDtos", trackingCjDtos);
                model.addAttribute("endTracking", trackingCjDtos.get(0));
                return "trackingCjList";
            } catch (IOException e) {
                return "error/deliveryerror";
            } catch (IndexOutOfBoundsException e) {
                return "error/deliveryerror";
            }
        }

        try {
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("apikey", "935ac5b93ac1faf3d8beb75c7b76dae0");
            parts.add("req_function","getTrackStatusALL");

            parts.add("send_data",hwbNo);

            CarrierInvoice carrierInvoice = carrierService.findByCarrierNumber(hwbNo);

            String data = restClientService.postFormUrlEncoded(parts);

            String[] responseDataSplit = data.split("\\|");

            List<TrackingDetailDto> trackingDetailDtos = new ArrayList<>();

            for(int i=3; i<responseDataSplit.length; i++) {
                String[] details = responseDataSplit[i].split(",");
                trackingDetailDtos.add(
                        TrackingDetailDto.builder()
                                .deliveryCon(details[3].replace("\"", ""))
                                .deliveryStatus(details[1].replace("\"", ""))
                                .deliveryStatusCode(details[0].replace("\"", ""))
                                .deliveryDate(details[2].replace("\"", ""))
                                .build()
                );
            }

            model.addAttribute("endStatus", trackingDetailDtos.get(trackingDetailDtos.size()-1));
            model.addAttribute("trackingDetailDto", trackingDetailDtos);
            model.addAttribute("hwbNo", carrierInvoice.getDeliveryOrder().getExternalNumber());

            return "trackingList";
        } catch (Exception e) {
            return "error/deliveryerror";
        }
    }

    @GetMapping("/multi")
    public String trackingInfoMulti() {
        //Model model, @RequestParam("file")MultipartFile file
        return "trackingMulti";
    }

    @PostMapping("/findmulti")
    public String findMulti(Model model, @RequestParam("file")MultipartFile file) {
        if (Fn.excelTypeCheck(file.getName())) {
            throw new IllegalArgumentException();
        }

        List<List<TrackingCjDto>> cjList = new ArrayList<>();
        List<List<TrackingDetailDto>> eftList = new ArrayList<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            if(sheet.getLastRowNum() > 1000) return  "/multi";

            Iterator<Row> rowIterator = sheet.rowIterator();
            List<String> hwbList = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    hwbList.add(cell.getStringCellValue());
                }
            }

            System.out.println("");

            List<CarrierInvoice> numbers =
                    carrierService.findByOrderNumberList(hwbList);
            for (CarrierInvoice carrierInvoice : numbers) {
                if (carrierInvoice.getNumber().startsWith("5")) {
                    try {
                        Document doc = Jsoup.connect("http://nexs.cjgls.com/web/info.jsp?slipno=" + carrierInvoice.getNumber()).get();
                        Objects.requireNonNull(doc);

                        Elements table = doc.select("table:eq(8)");
                        Objects.requireNonNull(table.isEmpty());// Select the 5th table using CSS selector

                        List<TrackingCjDto> trackingCjDtos = new ArrayList<>();
                        for (Element row : table.select("tr")) {
                            Elements cells = row.select("td");
                            if (cells.size() >= 5) {
                                TrackingCjDto trackingCjDto = TrackingCjDto.builder()
                                        .hwb(carrierInvoice.getNumber())
                                        .date(cells.get(0).text())
                                        .time(cells.get(1).text())
                                        .Area(cells.get(3).text())
                                        .status(cells.get(5).text()).build();

                                trackingCjDtos.add(trackingCjDto);
                            }
                        }
                        cjList.add(trackingCjDtos);

                        String endStatus = "1";

                        if (!trackingCjDtos.get(0).getArea().equals("Hub") && !trackingCjDtos.get(0).getArea().contains("HUB")) {
                            if (trackingCjDtos.get(0).getStatus().equals("간선상차")) endStatus = "1";
                            if (trackingCjDtos.get(0).getStatus().equals("집화처리")) endStatus = "2";
                            if (trackingCjDtos.get(0).getStatus().equals("배달출발")) endStatus = "7";
                            if (trackingCjDtos.get(0).getStatus().equals("배달완료")) endStatus = "8";
                        }

                        if (trackingCjDtos.get(0).getArea().contains("HUB")) {
                            if (trackingCjDtos.get(0).getStatus().equals("간선하차")) endStatus = "3";
                            if (trackingCjDtos.get(0).getStatus().equals("간선상차")) endStatus = "3";
                        }

                        if (trackingCjDtos.get(0).getArea().contains("Hub")) {
                            if (trackingCjDtos.get(0).getStatus().equals("간선하차")) endStatus = "4";
                            if (trackingCjDtos.get(0).getStatus().equals("간선상차")) endStatus = "4";
                        }

                    } catch (IOException e) {
                        return "error/deliveryerror";
                    } catch (IndexOutOfBoundsException e) {
                        return "error/deliveryerror";
                    }
                } else {
                    MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
                    parts.add("apikey", "935ac5b93ac1faf3d8beb75c7b76dae0");
                    parts.add("req_function", "getTrackStatusALL");

                    parts.add("send_data", carrierInvoice.getNumber());
                    String data = restClientService.postFormUrlEncoded(parts);

                    String[] responseDataSplit = data.split("\\|");

                    List<TrackingDetailDto> trackingDetailDtos = new ArrayList<>();

                    for (int i = 3; i < responseDataSplit.length; i++) {
                        String[] details = responseDataSplit[i].split(",");
                        trackingDetailDtos.add(
                                TrackingDetailDto.builder()
                                        .hwb(carrierInvoice.getDeliveryOrder().getExternalNumber())
                                        .deliveryCon(details[3].replace("\"", ""))
                                        .deliveryStatus(details[1].replace("\"", ""))
                                        .deliveryStatusCode(details[0].replace("\"", ""))
                                        .deliveryDate(details[2].replace("\"", ""))
                                        .build()
                        );
                    }
                    eftList.add(trackingDetailDtos);
                }
            }
        } catch (Exception e) {

        }
        model.addAttribute("eftList", eftList);
        model.addAttribute("cjList", cjList);

        return "trackingMultiFind";
    }
}
