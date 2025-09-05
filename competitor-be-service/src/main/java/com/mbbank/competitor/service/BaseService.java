package com.mbbank.competitor.service;

import com.mbbank.competitor.enums.BadRequestError;
import com.mbbank.competitor.parser.ParentParser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
//@RequiredArgsConstructor
public abstract class BaseService<T> {

    protected final ModelMapper mapper = new ModelMapper();

    protected final ParentParser parentParser = new ParentParser();


    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private RuntimeException notFoundException(long id) {
        return new RuntimeException(String.valueOf(id));
    }

    private Object castToRequiredType(Class fieldType, String value) {
        return parentParser.parse(fieldType, value);
    }

    private List<Object> castToRequiredType(Class fieldType, List<String> value) {
        try {
            return value.stream().map(v -> this.castToRequiredType(fieldType, v)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Class getFieldType(String fieldStr) {
        Class<T> tClass = this.getTClass();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldStr.contains(fieldName)) {
                return field.getType();
            }
        }
        return null;
    }

//    public void checkDuplicate(String field, String value, String collection){
//        Query query = new Query();
//        query.addCriteria(Criteria.where(field).is(value));
//        boolean check = mongoTemplate.exists(query, collection);
//        if (check)
//        {
//            throw BadRequestError.RECORD_NAME_ALREADY_EXIST.exception();
//        }
//    }


    public T reqToEntity(Object req, T entity) {
        try {
            Class<?> entityClass = entity.getClass();
            Field[] fields = entityClass.getDeclaredFields();
            Class<?> reqClass = req.getClass();
            List<String> reqFields = Arrays.stream(reqClass.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());
            for (Field field : fields) {
                String fieldName = field.getName();
                Field reqField = null;
                if (reqFields.contains(fieldName)) {
                    reqField = reqClass.getDeclaredField(fieldName);
                }
                if (Objects.isNull(reqField)) {
                    continue;
                }
                reqField.setAccessible(true);
                Object fieldValue = reqField.get(req);
                if (Objects.isNull(fieldValue)) {
                    continue;
                }
                Object value = getFieldValue(field, reqField, fieldValue);
                field.setAccessible(true);
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            throw BadRequestError.DATA_INVALID.exception();
        }
    }

    public <D> D mapping(Object req, D entity) {
        try {
            Class<?> entityClass = entity.getClass();
            Field[] fields = entityClass.getDeclaredFields();
            Class<?> reqClass = req.getClass();
            List<String> reqFields = Arrays.stream(reqClass.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());
            for (Field field : fields) {
                String fieldName = field.getName();
                Field reqField = null;
                if (reqFields.contains(fieldName)) {
                    reqField = reqClass.getDeclaredField(fieldName);
                }
                if (Objects.isNull(reqField)) {
                    continue;
                }
                reqField.setAccessible(true);
                Object fieldValue = reqField.get(req);
                if (Objects.isNull(fieldValue)) {
                    continue;
                }
                Object value = getFieldValue(field, reqField, fieldValue);
                field.setAccessible(true);
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            throw BadRequestError.DATA_INVALID.exception();
        }
    }

    public <D> D dataToResp(Object data, Class<D> respClass) {
        try {
            D entity = respClass.newInstance();
            Field[] fields = respClass.getDeclaredFields();
            Class<?> reqClass = data.getClass();
            List<String> reqFields = Arrays.stream(reqClass.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());
            for (Field field : fields) {
                String fieldName = field.getName();
                Field reqField = null;
                if (reqFields.contains(fieldName)) {
                    reqField = reqClass.getDeclaredField(fieldName);
                }
                if (Objects.isNull(reqField)) {
                    continue;
                }
                reqField.setAccessible(true);
                Object fieldValue = reqField.get(data);
                if (Objects.isNull(fieldValue)) {
                    continue;
                }
                Object value = getFieldValue(field, reqField, fieldValue);
                field.setAccessible(true);
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            return null;
        }
    }

    public <D> D entityToResp(Object source, Class<D> destinationType) {
        return this.mapper.map(source, destinationType);
    }

    private Object getFieldValue(Field entityField, Field reqField, Object value) {
        try {
            if (entityField.getType().isAssignableFrom(reqField.getType())) {
                return value;
            }
            return castToRequiredType(entityField.getType(), value.toString());
        } catch (Exception e) {
            log.info("Error parse req data");
        }
        return null;
    }

}
