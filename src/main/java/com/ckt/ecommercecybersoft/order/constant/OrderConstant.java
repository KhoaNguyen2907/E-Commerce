package com.ckt.ecommercecybersoft.order.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderConstant {
    @UtilityClass
    public class Order {
        public static final String TABLE_NAME = "order_table";
        public static final String COMMENT = "comment";
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String TOTAL_COST = "total_cost";
        public static final String TYPE = "type";
        public static final String PHONE = "phone";
    }

    @UtilityClass
    public class OrderItem {
        public static final String TABLE_NAME = "order_item";
        public static final String QUANTITY = "quantity";
        public static final String COST = "cost";
    }
}
