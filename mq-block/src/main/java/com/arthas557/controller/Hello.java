package com.arthas557.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RestController
public class Hello {

    @Autowired
    @Qualifier("jmsTemplatePublish")
    private JmsTemplate jmsTemplatePublish;

    @RequestMapping("/hello")
    public Res  hello(){

        for (int i = 0; i < 100; i++) {
            jmsTemplatePublish.convertAndSend("topic1", "Hello Spring topic 1");
        }
        return new Res("1","Success");
    }

    @JmsListener(destination = "topic1", containerFactory = "jmsListenerContainerFactoryTopic")
    public void receiveTopic(String message) {
        System.out.println("收到订阅消息：" + message);
    }



    public static class Res{
        private String code;
        private Object data;

        public Res(String code, Object data) {
            this.code = code;
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
