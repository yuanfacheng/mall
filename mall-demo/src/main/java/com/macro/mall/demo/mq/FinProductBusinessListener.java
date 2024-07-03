//package com.macro.mall.demo.mq;
//
//import java.util.Objects;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//import com.haier.cartech.cfsp.business.service.business.fin.FinProductBusinessService;
//import com.haier.cartech.cfsp.enterprise.dto.vo.FinProductBusinessMessage;
//import com.haier.cartech.cfsp.enterprise.dto.vo.PurchasePaymentMessage;
//import com.haier.cartech.cfsp.enterprise.service.IEnterpriseLoanApplicationService;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.MessageModel;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//
///**
// * @author yfc
// * @describe
// * @date 2024/6/23 13:12
// */
//@Component
//@RocketMQMessageListener(
//        topic = "${rocketmq.product-business.topic}", consumerGroup = "${rocketmq.product-business.consumer}", nameServer = "${rocketmq.product-business.nameSrv}",
//        selectorExpression = "*",
//        messageModel = MessageModel.CLUSTERING)
//public class FinProductBusinessListener implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
//
//
//    private final Logger logger = LoggerFactory.getLogger(FinProductBusinessListener.class);
//
//    @Autowired
//    private FinProductBusinessService finProductBusinessService;
//    @Override
//    @Transactional
//    public void onMessage(MessageExt ext) {
//        logger.info("FinProductBusinessListener监听到消息:{}", ext);
//        if (ext == null || ext.getBody() == null) {
//
//            logger.error("FinProductBusinessListener的参数为空");
//            return;
//        }
//
//        String msgBody = null;
//        FinProductBusinessMessage productBusinessMessage;
//        try {
//            msgBody = new String(ext.getBody());
//            productBusinessMessage = new Gson().fromJson(msgBody, FinProductBusinessMessage.class);
//            this.finProductBusinessService.updateByMMC(productBusinessMessage);
//            logger.info("FinProductBusinessListener:{}", msgBody);
//        } catch (JsonSyntaxException e) {
//            logger.error("FinProductBusinessListener：message：{}", msgBody, e);
//            e.printStackTrace();
//            return;
//        }
//
//
//
//    }
//
//    /**
//     * 设置当前实例的名称
//     */
//    @Override
//    public void prepareStart(DefaultMQPushConsumer consumer) {
//        consumer.setInstanceName("finProductBusinessListener");
//    }
//
//}
