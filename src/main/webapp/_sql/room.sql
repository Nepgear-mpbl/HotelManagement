#sql("findById")
SELECT * FROM j_room WHERE `id` = #para(0)
#end

#sql("findByName")
SELECT * FROM j_room WHERE `roomname` = #para(0)
#end

#sql("getAll")
SELECT * FROM j_room
#end

#sql("getUnusedWithMinSize")
SELECT * FROM j_room WHERE `belong` is null and `type` >= #para(0)
#end