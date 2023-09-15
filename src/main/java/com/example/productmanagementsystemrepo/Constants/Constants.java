package com.example.productmanagementsystemrepo.Constants;



public class Constants {
   public static class SupplierPayment {
       public static final String URL = "${supplier-payment.url}";
       public static final String NAME = "supplierPayment";
   }

    public static class Kafka {
        public static final String GROUP_ID = "${spring.kafka.consumer.group-id}";

        public static final String TOPICS = "${spring.kafka.consumer.topics}";
    }


}
