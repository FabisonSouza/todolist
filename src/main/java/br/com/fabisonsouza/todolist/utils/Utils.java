package br.com.fabisonsouza.todolist.utils;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {


    public void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getPropertyNames(source));
    }


    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptor();

        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null) {
                emptyNames.dd(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toarray(result);
    }
}