package carrotmoa.carrotmoa.entity;
import carrotmoa.carrotmoa.model.request.PaymentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    @Column(name = "partner_id")
    private Long partnerId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "imp_uid")
    private String impUid;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "merchant_uid")
    private String merchantUid;

    @Column(name = "pg_provider")
    private String pgProvider;

    @Column(name = "pg_type")
    private String pgType;

    @Column(name = "pg_tid")
    private String pgTid;

    @Column(name = "status")
    private String status;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "card_number")
    private String cardNumber;

    @PrePersist
    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
        paymentDate = LocalDateTime.now();
    }

//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }

    public static Payment of(PaymentRequest paymentRequest) {
        return Payment.builder()
                .partnerId(paymentRequest.getPartnerId())
                .userId(paymentRequest.getUserId())
                .orderId(paymentRequest.getOrderId())
                .impUid(paymentRequest.getImpUid())
                .paymentMethod(paymentRequest.getPayMethod())
                .merchantUid(paymentRequest.getMerchantUid())
                .paymentAmount(BigDecimal.valueOf(paymentRequest.getPaidAmount()))
                .pgProvider(paymentRequest.getPgProvider())
                .pgType(paymentRequest.getPgType())
                .pgTid(paymentRequest.getPgTid())
                .status(paymentRequest.getStatus())
                .cardName(paymentRequest.getCardName())
                .cardNumber(paymentRequest.getCardNumber())
                .build();

    }

}
