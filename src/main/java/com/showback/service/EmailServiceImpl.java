package com.showback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{


    private final JavaMailSender javaMailSender;

    private MimeMessage createMessage(String to, String ePw)throws Exception{
        System.out.println("보내는 대상 = " + to);
        System.out.println("인증 번호 = " + ePw);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        mimeMessage.addRecipients(Message.RecipientType.TO, to);
        mimeMessage.setSubject("Showday 계정 이메일 인증");

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> Showday </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요</p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.</p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong></div><br/> ";
        msgg+= "</div>";
        msgg+= "</div>";

        msgg += "<div class='footer_inner' style='margin:20px;'>";
        msgg += " =====================================================================================<br/>";
        msgg += "<span class='footer_title'>";
        msgg += "㈜쇼데이 주식회사";
        msgg += "</span>";
        msgg += "<address class='footer_address'>";
        msgg += "<p>주소: 0000001 서울특별시 관악구 봉천로 001, 쇼빌딩 대표이사: 김유진 사업자등록번호: 000-00-00000</p>";
        msgg += "<p>1000-0000 sunnyday@showday.co.kr 통신판매업 신고번호: 제 2023-서울-0001호 사업자정보확인 개인정보보호 책임자: 홍길동</p>";
        msgg += "</address>";
        msgg += "</div>";
        msgg += "<span class='footer_etc' style='margin:20px;'>";
        msgg += "Copyright © SHOW DAY Corporation. All rights reserved.";
        msgg += "</span>";

        mimeMessage.setText(msgg, "UTF-8", "html");
        mimeMessage.setFrom(new InternetAddress("Showday@showday.com", "Showday 발신전용"));

        return mimeMessage;
    }

    public static String createKey(){
        StringBuffer key = new StringBuffer();
        Random rand = new Random();

        for(int i = 0; i < 8; i++) {
            int index = rand.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (rand.nextInt(26)) + 97));
                    // a~z
                    break;
                case 1:
                    key.append((char) ((int) (rand.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rand.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String to) throws Exception {
        String ePw = createKey();
        MimeMessage mimeMessage = createMessage(to, ePw);
        try{
            javaMailSender.send(mimeMessage);
        } catch (MailException mailException) {
            mailException.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }
}
