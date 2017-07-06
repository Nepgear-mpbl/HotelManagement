#sql("findByName")
SELECT * FROM l_user WHERE `username` = #para(0)
#end