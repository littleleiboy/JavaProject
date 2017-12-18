package net.chenlin.dp.common.constant;

public class BaofooApiConstant {

    /**
     * 宝付交易类型
     */
    public enum BfTransactionType {

        /**
         * 后台交易
         */
        backTransaction("0431"),

        prepareBinding("11", BfTransactionType.backTransaction);

        private String value;
        private BfTransactionType parentType;

        BfTransactionType(String value, BfTransactionType parentType) {
            this.value = value;
            this.parentType = parentType;
        }

        BfTransactionType(String value) {
            this.value = value;
            this.parentType = null;
        }

        public String getValue() {
            return value;
        }

        public BfTransactionType getType() {
            return this;
        }

        public BfTransactionType getParentType() {
            return parentType;
        }
    }

}
