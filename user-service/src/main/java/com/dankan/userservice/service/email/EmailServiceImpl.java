package com.dankan.userservice.service.email;

import com.dankan.userservice.domain.User;
import com.dankan.userservice.dto.request.email.EmailCodeRequestDto;
import com.dankan.userservice.exception.user.EmailNotFoundException;
import com.dankan.userservice.exception.user.UserIdNotFoundException;
import com.dankan.userservice.repository.UserRepository;
import com.dankan.userservice.util.JwtUtil;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.HashMap;
import java.util.Random;

public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;
	private final String mail;
	private static HashMap<String, String> codes = new HashMap();
	private final UserRepository userRepository;

	public EmailServiceImpl(final JavaMailSender javaMailSender, final String mail, final UserRepository userRepository) {
		this.javaMailSender = javaMailSender;
		this.mail = mail;
		this.userRepository = userRepository;
	}

	// 메일 내용 작성
	public MimeMessage createMessage(String to) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("단칸 회원가입 이메일 인증");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<h1> 안녕하세요</h1>";
		msgg += "<h1> 대학생 단기양도 전문 플랫폼 단칸입니다</h1>";
		msgg += "<br>";
		msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
		msgg += "<br>";

		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "CODE : <strong>";
		msgg += codes.get(to) + "</strong><div><br/> "; // 메일에 인증번호 넣기
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
		message.setFrom(mail);// 보내는 사람

		return message;
	}

	// 랜덤 인증 코드 전송
	public String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				// a~z (ex. 1+97=98 => (char)98 = 'b')
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				// A~Z
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				// 0~9
				break;
			}
		}

		return key.toString();
	}

	// 메일 발송
	// sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
	// MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
	// 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
	public String sendSimpleMessage(String to) throws Exception {

		String ePw = createKey(); // 랜덤 인증번호 생성

		codes.put(to, ePw);

		MimeMessage message = createMessage(to); // 메일 발송
		try {// 예외처리
			javaMailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}


		return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
	}

	@Override
	public Boolean verifyCode(final EmailCodeRequestDto emailCodeRequestDto) {
		if(codes.get(emailCodeRequestDto.getEmail()).equals(emailCodeRequestDto.getCode())) {
			User user = userRepository.findUserByUserId(JwtUtil.getMemberId()).orElseThrow(
					() -> new UserIdNotFoundException(JwtUtil.getMemberId().toString())
			);

			user.setUnivEmail(emailCodeRequestDto.getEmail());

			userRepository.save(user);

			return true;
		}

		return false;
	}
}