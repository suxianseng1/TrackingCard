package com.nokida.trackingcard.webservice;


import com.alibaba.fastjson.JSON;
import com.nokida.trackingcard.exception.MyNullPointRunTimeException;
import com.nokida.trackingcard.service.DispatchCreateWordService;
import com.nokida.trackingcard.service.impl.DispatchCreateWordServiceImpl;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.HashMap;
import java.util.Map;

@WebService
public class TrackingCardWebService {

    public String createWordByJobNumber(String uuid,String jobNumber){
        DispatchCreateWordService createWordService = new DispatchCreateWordServiceImpl();
        Map<String,String> result = new HashMap<String, String>();
        try{
            String data = createWordService.createWordByJobNumber(uuid,jobNumber);
            result.put("status","1");
            result.put("data",data);
        } catch (MyNullPointRunTimeException me){
            result.put("status","0");
            result.put("message",me.getMessage());
        } catch (Exception e){
            result.put("status","0");
            result.put("message",e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    public String createWordBySingleCode(String wbs,String singleCode,String isBatchFlag){
        DispatchCreateWordService createWordService = new DispatchCreateWordServiceImpl();
        Map<String,String> result = new HashMap<String, String>();
        try{
            String data = createWordService.createWordBySingleCode(wbs,singleCode,isBatchFlag);
            result.put("status","1");
            result.put("data",data);
        } catch (MyNullPointRunTimeException me){
            result.put("status","0");
            result.put("message",me.getMessage());
        } catch (Exception e){
            result.put("status","0");
            result.put("message",e.getMessage());
        }
        return JSON.toJSONString(result);

    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8093/Service/TrackingCardWebService",new TrackingCardWebService());
        System.out.println("------ 跟踪卡生成 Word 服务开启,请勿关闭！！！ ------");
    }

}
