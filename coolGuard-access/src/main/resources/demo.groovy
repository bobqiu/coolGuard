def amount = accessRequest.getFloatData("N_F_transAmount");
amount += 10;
accessRequest.setDataByType("D_F_rhrehr", amount.toString(), FieldType.FLOAT);