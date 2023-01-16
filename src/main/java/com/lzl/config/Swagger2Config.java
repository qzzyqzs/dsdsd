package com.lzl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket apiConfig() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("仓库管理系统")
                //加载配置信息
                .apiInfo(apiInfo())
                //设置全局参数
                .globalOperationParameters(globalParamBuilder())
                //设置全局响应参数
                .globalResponseMessage(RequestMethod.GET,responseBuilder())
                .globalResponseMessage(RequestMethod.POST,responseBuilder())
                .globalResponseMessage(RequestMethod.PUT,responseBuilder())
                .globalResponseMessage(RequestMethod.DELETE,responseBuilder())
                .select()
                //加载swagger 扫描包
                .apis(RequestHandlerSelectors.basePackage("com.lzl.controller"))
                .paths(PathSelectors.any()).build()
                //设置安全认证
                .securitySchemes(security());

    }

    /**
     * 获取swagger创建初始化信息
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("雷宗林", "www.baidu.com", "2660543830@qq.com");
        return new ApiInfoBuilder().title("仓库管理系统").description("仓库管理系统相关API").contact(contact)
                .version("1.0").build();
    }

    private List<ApiKey> security(){
        List<ApiKey> list=new ArrayList<>();
        list.add(new ApiKey("token","token","header"));
        return list;
    }

    /**
     * 构建全局参数列表
     * @return
     */
    private List<Parameter> globalParamBuilder(){
        List<Parameter> pars = new ArrayList<>();
        pars.add(parameterBuilder("token","令牌","string","header",false).build());
        return pars;
    }

    /**
     * 创建参数
     * @return
     */
    private ParameterBuilder parameterBuilder(String name, String desc, String type , String parameterType, boolean required) {
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(name).description(desc).modelRef(new ModelRef(type)).parameterType(parameterType).required(required).build();
        return tokenPar;
    }

    /**
     * 创建全局响应值
     * @return
     */
    private List<ResponseMessage> responseBuilder() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(200).message("响应成功").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").build());
        return responseMessageList;
    }
}
