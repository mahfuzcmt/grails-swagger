package com.hmtmcse.gs

import com.hmtmcse.gs.data.GsAction
import com.hmtmcse.gs.data.GsApiNestedResponseResponse
import com.hmtmcse.gs.data.GsApiRequestProperty
import com.hmtmcse.gs.data.GsApiResponseData
import com.hmtmcse.gs.data.GsApiResponseProperty
import com.hmtmcse.gs.data.GsDomain
import com.hmtmcse.gs.model.CustomProcessor


class GsApiActionDefinition<T> {


    private Map<String, GsApiResponseProperty> responseProperties = new LinkedHashMap<>()
    private Map<String, GsApiRequestProperty> requestProperties = new LinkedHashMap<>()
    public String description = null
    public String modelDefinition = null
    public String summary = null
    public String responseType = null
    public CustomProcessor customProcessor = null
    public Boolean enableWhere = false
    public Class<T> domain

    public String parameterDescription = null
    public String parameterName = null
    public GsApiResponseData successResponseFormat = null
    public GsApiResponseData failedResponseFormat = null
    public List whereAllowedPropertyList = []
    public Map whereAllowedPropertyMap = [:]
    public LinkedHashMap<String, GsApiNestedResponseResponse> nested = new LinkedHashMap<>()
    private GsDomain gsDomain


    public GsApiActionDefinition(){}

    public GsApiActionDefinition(Class<T> domain){
        this.domain = domain
    }


    public Map domainFields(){
        gsDomain = GsReflectionUtil.getDomainToDomainProperties(this.domain)
        return  GsReflectionUtil.getDomainToSwaggerDataType(this.domain)
    }


    public static GsApiActionDefinition instance(Class<T> domain){
        return new GsApiActionDefinition(domain)
    }

    public GsApiActionDefinition<T> successResponseAsData(Integer code = null){
        successResponseFormat = GsApiResponseData.successResponse([:], code)
        return this
    }


    public GsApiResponseProperty addResponseProperty(String name, String alias = null, String defaultValue = ""){
        responseProperties.put(name, new GsApiResponseProperty(name).setAlias(alias).setDefaultValue(defaultValue))
        return responseProperties.get(name)
    }


    public GsApiRequestProperty addRequestProperty(String name, String dataType = null, String defaultValue = "") {
        requestProperties.put(name, new GsApiRequestProperty(name).setDataType(dataType).setDefaultValue(defaultValue))
        return requestProperties.get(name)
    }


    public GsApiActionDefinition<T> conditionAllowedProperty(List<String> fields){
        fields?.each { String field ->
            whereAllowedPropertyMap.put(field, true)
        }
        whereAllowedPropertyList = fields
        return this
    }


    public GsApiActionDefinition<T> excludeProperty(List<String> fields){
        Map exclude = [:]
        fields?.each { String field ->
            exclude.put(field, true)
        }
        String key
        domainFields()?.each { field ->
            key = field.getKey() as String
            responseProperties.put(key, new GsApiResponseProperty(key))
        }
        return this
    }


    public GsApiActionDefinition<T> includeOnlyProperty(List<String> fields){
        fields?.each { String field ->
            responseProperties.put(field, new GsApiResponseProperty(field))
        }
        return this
    }


    public getRequestProperties(){
        return requestProperties
    }


    public getResponseProperties(){
        return responseProperties
    }

    public setModelDefinition(String apiVersion, String controller, GsAction gsAction){
        this.modelDefinition = "${GsUtil.makeHumReadble(apiVersion)}${GsUtil.makeHumReadble(controller)}${GsUtil.makeHumReadble(gsAction.httpMethod)}${GsUtil.makeHumReadble(gsAction.name)}"
    }


    private GsApiResponseProperty addHasManyOrOneResponse(String name, Boolean isMany) {
        GsApiNestedResponseResponse gsApiNestedResponse = new GsApiNestedResponseResponse()
        gsApiNestedResponse.isList = isMany
        nested.put(name, gsApiNestedResponse)
        return nested.get(name).gsApiResponseProperty
    }


    GsApiResponseProperty addHasManyResponse(String name) {
        return addHasManyOrOneResponse(name, true)
    }


    GsApiResponseProperty addHasOneResponse(String name) {
        return addHasManyOrOneResponse(name, false)
    }


    private GsApiRequestProperty addHasManyOrOneRequest(String name, Boolean isMany) {
        GsApiNestedResponseResponse gsApiNestedRequest = new GsApiNestedResponseResponse()
        gsApiNestedRequest.isList = isMany
        nested.put(name, gsApiNestedRequest)
        return nested.get(name).gsApiRequestProperty
    }


    GsApiRequestProperty addHasManyRequest(String name) {
        return addHasManyOrOneRequest(name, true)
    }


    GsApiRequestProperty addHasOneRequest(String name) {
        return addHasManyOrOneRequest(name, false)
    }
}
