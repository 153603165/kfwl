package com.kfwl.apiconfig;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * User: 丰帆
 * Date: 2018/6/18
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class UserConfig {

    @SuppressWarnings("unchecked")
	@Bean
    public Docket channelApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户Api接口")
                .genericModelSubstitutes(DeferredResult.class)
//                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/api/user(/.*)?")))//过滤的接口
                .build()
                .apiInfo(apiInfo());
    }

    @SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("用户Api接口",//标题
                "",
                "1",//版本
                "",
                "丰帆153603165@qq.com>",//作者
                "卡服物流科技",//链接显示文字
                "http://localhost:9090"//网站链接
        );
        return apiInfo;
    }

}
