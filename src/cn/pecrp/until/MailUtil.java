package cn.pecrp.until;

import  java.util.Properties;

import  org.springframework.mail.SimpleMailMessage; 
import  org.springframework.mail.javamail.JavaMailSenderImpl; 

public class MailUtil {
	
	private JavaMailSenderImpl senderImpl;
	public void setSenderImpl(JavaMailSenderImpl senderImpl) {
		this.senderImpl = senderImpl;
	}
	
	private SimpleMailMessage mailMessage;
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	
	private Properties prop;
	public void setProp(Properties prop) {
		this.prop = prop;
	}


	public boolean sendMail (String to,String text) {
		System.out.println("sendMail...util...");
		
		try{
			//设定mail server
			senderImpl.setHost("smtp.163.com");
			
			// 设置收件人，寄件人 用数组发送多个邮件
			// String[] array = new String[]    {"sun111@163.com","sun222@sohu.com"};    
			// mailMessage.setTo(array);    
			
			mailMessage.setTo(to); 
		    mailMessage.setFrom( "15850685753@163.com" ); 
		    mailMessage.setSubject( "体育教学分类推荐平台邮箱验证码" ); 
		    mailMessage.setText("发自体育教学分类推荐平台,这是您的验证码:  "+text+" ,10分钟之内有效。"); 
			
		    senderImpl.setUsername("15850685753@163.com");
		    senderImpl.setPassword("L19970604");
		    
		    prop.put("mail.smtp.auth","true");
		    prop.put("mail.smtp.timeout","25000");
		    senderImpl.setJavaMailProperties(prop);
		    
		    //发送邮件
		    senderImpl.send(mailMessage);
		    
		    System.out.println("发送邮件成功");
		    
		    return true;
		}catch (Exception e) {
			System.out.println("发送邮件失败");
			return false;
		}
	}

}
