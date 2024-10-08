//package carrotmoa.carrotmoa.config;
//import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
//import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
//import org.junit.jupiter.api.Test;
//public class JasyptConfigTest2 {
//    @Test
//    public void jasypt_test() {
//        // Jasypt 설정
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword(""); // 암호화 및 복호화에 사용할 비밀번호
//        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setKeyObtentionIterations("1000");
//        config.setPoolSize("1");
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
//        config.setStringOutputType("base64");
//        encryptor.setConfig(config);
//        // 암호화할 값들
//        String datasourceUrl = ""; // 데이터베이스 URL
//        String username = ""; // 사용자 이름
//        String password = ""; // 비밀번호
//        String accessKey = ""; // AWS 액세스 키
//        String secretKey = ""; // AWS 시크릿 키
//        // 암호화
//        String encryptDatasourceUrl = encryptor.encrypt(datasourceUrl);
//        String encryptUsername = encryptor.encrypt(username);
//        String encryptPassword = encryptor.encrypt(password);
//        String encryptAccessKey = encryptor.encrypt(accessKey);
//        String encryptSecretKey = encryptor.encrypt(secretKey);
//        // 암호화 결과 출력
//        System.out.println("Encrypted datasource.url: " + encryptDatasourceUrl);
//        System.out.println("Encrypted spring.datasource.username: " + encryptUsername);
//        System.out.println("Encrypted spring.datasource.password: " + encryptPassword);
//        System.out.println("Encrypted cloud.aws.credentials.accessKey: " + encryptAccessKey);
//        System.out.println("Encrypted cloud.aws.credentials.secretKey: " + encryptSecretKey);
//    }
//}
