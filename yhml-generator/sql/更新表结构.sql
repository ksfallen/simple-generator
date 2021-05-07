-- 新增字段
alter table voucher_template
    add single_limit int(8) default 0 null after floor_amount;
