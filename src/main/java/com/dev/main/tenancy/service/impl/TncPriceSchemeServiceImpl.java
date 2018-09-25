package com.dev.main.tenancy.service.impl;

import com.dev.main.tenancy.dao.TncPriceSchemeMapper;
import com.dev.main.tenancy.domain.TncPriceScheme;
import com.dev.main.tenancy.service.ITncPriceSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TncPriceSchemeServiceImpl implements ITncPriceSchemeService {

    @Autowired
    private TncPriceSchemeMapper tncPriceSchemeMapper;

    @Override
    public int addPriceScheme(TncPriceScheme tncPriceScheme) {
        tncPriceScheme.setIsDeleted(new Byte("0"));
        tncPriceScheme.setGmtCreate(new Date());
        tncPriceScheme.setGmtModified(new Date());
        return tncPriceSchemeMapper.insertSelective(tncPriceScheme);
    }


}
