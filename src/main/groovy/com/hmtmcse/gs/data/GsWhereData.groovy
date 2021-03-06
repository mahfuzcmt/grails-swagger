package com.hmtmcse.gs.data

class GsWhereData {

    public LinkedHashMap<String, Object> equal = [:]
    public LinkedHashMap<String, Object> notEqual = [:]
    public LinkedHashMap<String, Object> lessThan = [:]
    public LinkedHashMap<String, Object> lessThanEqual = [:]
    public LinkedHashMap<String, Object> greaterThan = [:]
    public LinkedHashMap<String, Object> greaterThanEqual = [:]
    public LinkedHashMap<String, Object> like = [:]
    public LinkedHashMap<String, Object> order = [:]
    public LinkedHashMap<String, LinkedHashMap<Object, Object>> between = [:]
    public Boolean count = false
    public LinkedHashMap<String, List<Object>> inList  = []
    public List<Object> select = []
    public List<String> isNull = []
    public List<String> isNotNull = []
    public GsWhereData and = null
    public GsWhereData or = null


    public GsWhereData addOrderBy(String field, Object data) {
        order.put(field, data)
        return this
    }

    public GsWhereData addEqual(String field, Object data) {
        equal.put(field, data)
        return this
    }

    public GsWhereData addInList(String field, List<Object> list) {
        if (inList.get(field) != null){
            inList.get(field).addAll(list)
        }else{
            inList.put(field, list)
        }
        return this
    }

    public GsWhereData addAnd() {
        and = and ?: new GsWhereData()
        return and
    }

    public GsWhereData addOr() {
        or = or ?: new GsWhereData()
        return or
    }

    public GsWhereData addIsNull(String field) {
        isNull.add(field)
        return this
    }

    public GsWhereData addIsNotNull(String field) {
        isNotNull.add(field)
        return this
    }

    public GsWhereData addNotEqual(String field, Object data) {
        notEqual.put(field, data)
        return this
    }


    public GsWhereData addLessThan(String field, Object data) {
        lessThan.put(field, data)
        return this
    }


    public GsWhereData addLessThanEqual(String field, Object data) {
        lessThanEqual.put(field, data)
        return this
    }


    public GsWhereData addGreaterThan(String field, Object data) {
        greaterThan.put(field, data)
        return this
    }


    public GsWhereData addGreaterThanEqual(String field, Object data) {
        greaterThanEqual.put(field, data)
        return this
    }

}
