#sql("findById")
SELECT * FROM j_order WHERE `id` = #para(0)
#end
#sql("getByState")
SELECT * FROM j_order WHERE `state` = #para(0)
#end
#sql("getByStateWithType")
SELECT j_order.*,j_type.type FROM j_order INNER JOIN j_type ON j_order.type=j_type.id WHERE j_order.state = #para(0)
#end