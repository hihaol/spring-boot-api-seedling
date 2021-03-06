package com.zoctan.seedling.service.impl;

import com.zoctan.seedling.core.service.AbstractService;
import com.zoctan.seedling.mapper.UserRoleMapper;
import com.zoctan.seedling.model.UserRole;
import com.zoctan.seedling.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Zoctan
 * @date 2018/5/27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends AbstractService<UserRole> implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

}
