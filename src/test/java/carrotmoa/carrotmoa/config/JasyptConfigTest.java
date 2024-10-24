package carrotmoa.carrotmoa.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {
    @Test
    public void jasypt_test() {
        // 테스트할 값들 (RDS 정보)
        String endpoint = "jdbc:mysql://carrot-moa.czioa0eq2ly1.ap-northeast-2.rds.amazonaws.com:3306/carrot_moa"; // 엔드포인트 입력
        String username = ""; // 사용자 이름 입력
        String password = ""; //비번 입력

        // Jasypt 설정
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(""); // Jasypt 암호화 및 복호화 과정에서 사용되는 비밀번호 설정

        // 암호화 알고리즘 및 설정
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        // 엔드포인트 암호화
        System.out.println("endpoint : " + endpoint);
        String encryptEndpoint = encryptor.encrypt(endpoint);
        System.out.println("encryptEndpoint : " + encryptEndpoint);

        // 사용자 이름 암호화
        System.out.println("username: " + username);
        String encryptUsername = encryptor.encrypt(username);
        System.out.println("encryptUsername : " + encryptUsername);

        // 비밀번호 암호화
        System.out.println("password: " + password);
        String encryptPassword = encryptor.encrypt(password);
        System.out.println("encryptPassword : " + encryptPassword);

        // 복호화
        String decrpytEndpoint = encryptor.decrypt(encryptEndpoint);
        String decryptUsername = encryptor.decrypt(encryptUsername);
        String decryptPassword = encryptor.decrypt(encryptPassword);

        // 복호화 결과 출력
        System.out.println("decryptEndpoint: " + decrpytEndpoint);
        System.out.println("decryptUsername : " + decryptUsername);
        System.out.println("decryptPassword : " + decryptPassword);

        // 원래 텍스트와 복호화된 텍스트 비교
        System.out.println(endpoint.equals(decrpytEndpoint));
        System.out.println(username.equals(decryptUsername));
        System.out.println(password.equals(decryptPassword));

    }
}
