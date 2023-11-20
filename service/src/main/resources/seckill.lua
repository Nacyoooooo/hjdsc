local parkId=ARGV[1]
local userId=ARGV[2]
local stockKey='caputered:stock:'..parkId
local orderKey='caputered:order:'..parkId

if(tonumber(redis.call('get',stockKey))<=0)then
    return 1
end
if (redis.call('sismember',orderKey,userId)==1) then
    return 2
end
redis.call('incrby',stockKey,-1)
redis.call('sadd',orderKey,userId)

return 0