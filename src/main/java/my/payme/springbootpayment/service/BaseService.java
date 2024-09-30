package my.payme.springbootpayment.service;

import my.payme.springbootpayment.entity.BaseEntity;

public interface BaseService <E extends BaseEntity,Response,Request> {

    E mapRequestsToEntity(Request request);
    Response mapResponseToEntity(E entity);
}
