select t.pid,t.terminal_id,t.bo_term_id,t.branch,wa.disp_load1,wa.disp_load2,wa.disp_load3,wa.disp_load4,
sum(dt.d1val*wa.disp_load1+dt.d2val*wa.disp_load2+dt.d3val*wa.disp_load3) Amtusd,sum(dt.d4val*wa.disp_load4)amtriel

from SVISTA.v_web_atm_status wa
left join SVISTA.t_term_def t on wa.pid = t.pid
left join SVISTA.DEF_TAB dt on wa.pid = dt.id
left join SVISTA.ISO_CURRENCY_CODES cu1 on dt.d1type = cu1.code_n3
left join SVISTA.ISO_CURRENCY_CODES cu2 on dt.d2type = cu2.code_n3
left join SVISTA.ISO_CURRENCY_CODES cu3 on dt.d3type = cu3.code_n3
left join SVISTA.ISO_CURRENCY_CODES cu4 on dt.d4type = cu4.code_n3

group by t.pid,t.terminal_id,t.bo_term_id,t.branch,wa.disp_load1,wa.disp_load2,wa.disp_load3,wa.disp_load4
order by t.pid asc