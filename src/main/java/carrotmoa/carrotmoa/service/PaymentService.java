package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Payment;
import carrotmoa.carrotmoa.entity.Reservation;
import carrotmoa.carrotmoa.model.request.PaymentRequest;
import carrotmoa.carrotmoa.model.request.ReservationRequest;
import carrotmoa.carrotmoa.repository.PaymentRepository;
import carrotmoa.carrotmoa.repository.ReservationRepository;
import carrotmoa.carrotmoa.util.PaymentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentClient paymentClient;
    private final ReservationRepository reservationRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentClient paymentClient, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentClient = paymentClient;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public void processPaymentAndReservation(PaymentRequest paymentRequest, ReservationRequest reservationRequest) {
        // 결제 정보 저장
        Payment payment = savePayment(paymentRequest);

        // 상태가 "paid"인 경우에만 예약 저장
        if ("paid".equals(payment.getStatus())) {
            Reservation reservation = saveReservation(reservationRequest);
            payment.setReservationId(reservation.getId());
            paymentRepository.save(payment);
        }
    }

    public Payment savePayment(PaymentRequest paymentRequest) {
        Payment payment = paymentRequest.toPaymentEntity();
        return paymentRepository.save(payment);
    }

    public Reservation saveReservation(ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.builder()
                .accommodationId(reservationRequest.getAccommodationId())
                .userId(reservationRequest.getUserId())
                .checkInDate(reservationRequest.getCheckInDate())
                .checkOutDate(reservationRequest.getCheckOutDate())
                .totalPrice(reservationRequest.getTotalPrice())
                .status(reservationRequest.getStatus())
                .build();

        return reservationRepository.save(reservation);
    }

    /**
     * 모든 결제 내역 조회
     *
     * @return 모든 Payment 엔티티 리스트
     */
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    /**
     * 새로운 결제 내역 저장
     *
     * @param payment 저장할 Payment 엔티티
     * @return 저장된 Payment 엔티티
     */

//    @Transactional
//    public Payment savePayment(Payment payment) {
//        return paymentRepository.save(payment);
//    }

    /**
     * 결제 내역 삭제
     *
     * @param uid 포트원 거래고유번호
     */
    @Transactional
    public void canclePayment(String uid) {
        // 외부 API로 결제 취소 요청
        paymentClient. cancelPayment(uid);

        //impUid로 Payment 엔티티 조회
        Payment payment = paymentRepository.findByImpUid(uid)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with impUid: " + uid));

        // status 필드를 "cancel"로 변경
        payment.setStatus("cancel");

    }



}
