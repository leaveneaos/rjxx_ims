package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.XmlJaxbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName TestCRESTVSKP
 * @Description TODO
 * @Author 许黎明
 * @Date 2018-04-27 19:40
 * @Version 1.0
 **/
@Controller
@RequestMapping("/testSkp")
public class TestSkp extends BaseController {



    @Autowired
    private SkService skService;

    @RequestMapping("/register")
    @ResponseBody
    public String register(String skpid) throws Exception {
        return skService.register(Integer.valueOf(skpid));
    }

    @RequestMapping("/inputUDiskPassword")
    @ResponseBody
    public String inputUDiskPassword(String skpid) throws Exception {
        return skService.inputUDiskPassword(Integer.valueOf(skpid));
    }

    @RequestMapping("/deviceState")
    @ResponseBody
    public String deviceState(String skpid) throws Exception {
        return skService.deviceState(Integer.valueOf(skpid));
    }

    @RequestMapping("/GetUploadStates")
    @ResponseBody
    public String GetUploadStates(String skpid) throws Exception {
        return skService.GetUploadStates(Integer.valueOf(skpid));
    }

    @RequestMapping("/TriggerUpload")
    @ResponseBody
    public String TriggerUpload(String skpid) throws Exception {
        return skService.TriggerUpload(Integer.valueOf(skpid));
    }

    @RequestMapping("/GetDeclareTaxStates")
    @ResponseBody
    public String GetDeclareTaxStates(String skpid) throws Exception {
        return skService.GetDeclareTaxStates(Integer.valueOf(skpid));
    }

    @RequestMapping("/TriggerDeclareTax")
    @ResponseBody
    public String TriggerDeclareTax(String skpid) throws Exception {
        return skService.TriggerDeclareTax(Integer.valueOf(skpid));
    }

    @RequestMapping("/UDiskInfo")
    @ResponseBody
    public String UDiskInfo(String skpid) throws Exception {
        return skService.UDiskInfo(Integer.valueOf(skpid));
    }

    @RequestMapping("/InvoiceControlInfo")
    @ResponseBody
    public String InvoiceControlInfo(String skpid) throws Exception {
        return skService.InvoiceControlInfo(Integer.valueOf(skpid));
    }

    @RequestMapping("/GetAllInvoiceSections")
    @ResponseBody
    public String GetAllInvoiceSections(String skpid) throws Exception {
        return skService.GetAllInvoiceSections(Integer.valueOf(skpid));
    }

    @RequestMapping("/InvoiceDistribute")
    @ResponseBody
    public String InvoiceDistribute(Map skpMap) throws Exception {
        return skService.InvoiceDistribute(skpMap);
    }

    @RequestMapping("/UDiskBinding")
    @ResponseBody
    public String UDiskBinding(int skpid) throws Exception {
        return skService.UDiskBinding(skpid);
    }

    @RequestMapping("/SwitchUDisk")
    @ResponseBody
    public String SwitchUDisk(int skpid) throws Exception {
        return skService.SwitchUDisk(skpid);
    }

    @RequestMapping("/DeviceInfo")
    @ResponseBody
    public String DeviceInfo(int skpid) throws Exception {
        return skService.DeviceInfo(skpid);
    }

    @RequestMapping("/FactoryReset")
    @ResponseBody
    public String FactoryReset(int skpid) throws Exception {
        return skService.FactoryReset(skpid);
    }

    @RequestMapping("/skInvoiceQuery")
    @ResponseBody
    public String skInvoiceQuery(int kplsh) throws Exception {
        return skService.skInvoiceQuery(kplsh);
    }

    @RequestMapping("/InvalidateInvoice")
    @ResponseBody
    public String InvalidateInvoice(int kplsh) throws Exception {
        return skService.InvalidateInvoice(kplsh);
    }

    @RequestMapping("/GetCurrentInvoiceInfo")
    @ResponseBody
    public String GetCurrentInvoiceInfo(String fpzldm, int skpid) throws Exception {
        InvoiceResponse invoiceResponse=skService.getCodeAndNo(skpid,fpzldm);
        return XmlJaxbUtils.toXml(invoiceResponse);
    }
}
