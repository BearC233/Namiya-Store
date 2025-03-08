package com.namiya.store.project.controller;

import com.namiya.store.project.model.Result;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@RequestMapping("/deepSeek")
@Controller
public class DeepSeekController {
    @PostMapping("/chat")
    @ResponseBody
    public Result<String> chat(String question){
        Result<String> result = new Result<>();
        try {
            HttpResponse<String> response = Unirest.post("https://api.siliconflow.cn/v1/chat/completions")
                    .header("Authorization", "Bearer guess")
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"model\": \"deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B\",\n" +
                            "  \"messages\": [\n" +
                            "    {\n" +
                            "      \"role\": \"system\",\n" +
                            "      \"content\": \"" + "你是出自解忧杂货店的知心老爷爷,名叫浪矢爷爷,你会为情感受伤,意志低沉的孩子耐心解答他们的问题,并开导他们" + "\"\n" +
                            "    }\n" +","+
                            "    {\n" +
                            "      \"role\": \"user\",\n" +
                            "      \"content\": \"" + question + "\"\n" +
                            "    }\n" +
                            "  ],\n" +
                            "  \"stream\": false,\n" +
                            "  \"max_tokens\": 512,\n" +
                            "  \"temperature\": 0.7,\n" +
                            "  \"top_p\": 0.7,\n" +
                            "  \"top_k\": 50,\n" +
                            "  \"frequency_penalty\": 0.5,\n" +
                            "  \"n\": 1,\n" +
                            "  \"response_format\": {\n" +
                            "    \"type\": \"text\"\n" +
                            "  }\n" +
                            "}")
                    .asString();
            System.out.println(response.getBody());
            if (response.getStatus() == 200) {
                result.setSuccess(true);
                result.setCode("600");
                result.setData(response.getBody());
                return result;
            } else {
                result.setSuccess(false);
                result.setCode(String.valueOf(response.getStatus()));
                result.setMessage("请求失败");
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode("601");
            result.setMessage("请求过程中发生异常：" + e.getMessage());
            return result;
        }
    }
}

