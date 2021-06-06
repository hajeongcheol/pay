package lecture;

import lecture.config.kafka.KafkaProcessor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler {
    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverClassCanceled_CancelPayment(@Payload ClassCanceled classCanceled) {

        if (classCanceled.isMe()) {
            List<Payment> paymentList = paymentRepository.findByClassId(classCanceled.getId());

            for (Payment payment : paymentList) {
                payment.setStatus("CANCEL");
                paymentRepository.save(payment);
            }
        }
    }

}
