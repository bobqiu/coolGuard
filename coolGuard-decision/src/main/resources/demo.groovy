def amount = decisionRequest.getFloatData("N_F_transAmount");
amount += 10;
decisionRequest.setDataByType("D_F_rhrehr", amount.toString(), FieldType.FLOAT);