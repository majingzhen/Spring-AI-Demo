package com.matuto.springaidemo.functions;

import org.apache.commons.lang3.StringUtils;
import java.util.function.Function;

public class LocationWeatherFunction implements Function<LocationWeatherFunction.Request, LocationWeatherFunction.Response> {

    // 接收gpt提取后的信息，自动调用该方法
    @Override
    public Response apply(Request request) {
        // 取决于模型的智能化程度不同，有的模型需要判断，有的则不用
        if (StringUtils.isEmpty(request.location())){
            return new Response("参数缺失，无须调用 function-all, 正常响应即可");
        }
        // 调用接口
        System.out.println("调用天气接口");
        return new Response(request.location + "当前为晴天!");
    }

    // 密封类 - 自动生成参数的 get set 方法及带参的构造方法，并且不允许继承
    // 负责告诉 AI 模型要提取哪些关键信息, 接收
    public record Request(String location){}


    // 最终响应 GPT
    public record Response(String message){}
}
