# 一、订单

订单状态列表:

1. `NEWLY_CREATED` 新建
2. `TAKEN_UP` 已有人抢单
3. `SEND_INVITATION` 已发送合作邀请并等待结果
4. `INVITATION_ACCEPTED` 合作邀请已接受
5. `INVITATION_REJECTED` 合作邀请已拒绝
6. `IN_PROGRESS` 订单开始工作中
7. `FINISHED` 订单已结束
8. `COMMENTED` 订单已评论
9. `CANCELED` 订单已取消

## 1. 已完成且作为主责任人订单列表

### URL及请求方法
`GET /api/mobile/technician/order/listMain`

### 请求参数

| 参数名称 | 是否必须 | 说明 | 举例 |
| ------ | -------- | ---- | --- |
| page | 否 | 分页页码, 从1开始，默认为1 | 1 |
| pageSize | 否 | 每页条数, 默认20 | 20 |


### 返回数据

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "page": 1,
        "totalElements": 2,
        "totalPages": 1,
        "pageSize": 20,
        "count": 2,
        "list": [
            {
                "id": 47,
                "orderNum": "20160303120243AS54GE",
                "orderType": 0,
                "photo": null,
                "orderTime": null,
                "addTime": 1456977763758,
                "creatorType": 0,
                "creatorId": 0,
                "creatorName": null,
                "contactPhone": null,
                "positionLon": null,
                "positionLat": null,
                "remark": null,
                "mainTechId": 1,
                "secondTechId": 0,
                "status": "NEWLY_CREATED"
            },
            {
                "id": 1,
                "orderNum": "20160223134200014567",
                "orderType": 1,
                "photo": "",
                "orderTime": 1456293600000,
                "addTime": 1456196963000,
                "creatorType": 1,
                "creatorId": 1,
                "creatorName": null,
                "contactPhone": null,
                "positionLon": null,
                "positionLat": null,
                "remark": "bababala",
                "mainTechId": 1,
                "secondTechId": 2,
                "status": null
            }
        ]
    }
}
```

data字段是一个典型的分页对象, 请参考 [帐户及登录 - 10. 查询技师](1-1-account.md)

## 2. 已完成且作为次责任人订单列表

### URL及请求方法
`GET /api/mobile/technician/order/listSecond`

### 请求参数

| 参数名称 | 是否必须 | 说明 | 举例 |
| ------ | -------- | ---- | --- |
| page | 否 | 分页页码, 从1开始，默认为1 | 1 |
| pageSize | 否 | 每页条数, 默认20 | 20 |


### 返回数据

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "page": 1,
        "totalElements": 1,
        "totalPages": 1,
        "pageSize": 20,
        "count": 1,
        "list": [
            {
                "id": 2,
                "orderNum": "20160223135200016789",
                "orderType": 2,
                "photo": null,
                "orderTime": 1456293600000,
                "addTime": 1456196963000,
                "creatorType": 1,
                "creatorId": 2,
                "creatorName": null,
                "contactPhone": null,
                "positionLon": null,
                "positionLat": null,
                "remark": "somewords",
                "mainTechId": 2,
                "secondTechId": 1,
                "status": null
            }
        ]
    }
}
```

## 3. 获取未完成订单列表（包括主,次责任人订单）

### URL及请求方法
`GET /api/mobile/technician/order/listUnfinished`

### 请求参数

| 参数名称 | 是否必须 | 说明 | 举例 |
| ------ | -------- | ---- | --- |
| page | 否 | 分页页码, 从1开始，默认为1 | 1 |
| pageSize | 否 | 每页条数, 默认20 | 20 |


### 返回数据

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "page": 1,
        "totalElements": 3,
        "totalPages": 1,
        "pageSize": 20,
        "count": 3,
        "list": [
            {
                "id": 3,
                "orderNum": "20160303145128PU4XYS",
                "orderType": 0,
                "photo": null,
                "orderTime": null,
                "addTime": 1456987888607,
                "creatorType": 0,
                "creatorId": 0,
                "creatorName": null,
                "contactPhone": null,
                "positionLon": null,
                "positionLat": null,
                "remark": null,
                "mainTechId": 1,
                "secondTechId": 0,
                "status": "NEWLY_CREATED"
            },
            {
                "id": 2,
                "orderNum": "20160223135200016789",
                "orderType": 2,
                "photo": null,
                "orderTime": 1456293600000,
                "addTime": 1456196963000,
                "creatorType": 1,
                "creatorId": 2,
                "creatorName": null,
                "contactPhone": null,
                "positionLon": null,
                "positionLat": null,
                "remark": "somewords",
                "mainTechId": 2,
                "secondTechId": 1,
                "status": "TAKEN_UP"
            },
            {
                "id": 1,
                "orderNum": "20160223134200014567",
                "orderType": 1,
                "photo": "",
                "orderTime": 1456293600000,
                "addTime": 1456196963000,
                "creatorType": 1,
                "creatorId": 1,
                "creatorName": null,
                "contactPhone": null,
                "positionLon": null,
                "positionLat": null,
                "remark": "bababala",
                "mainTechId": 1,
                "secondTechId": 2,
                "status": "TAKEN_UP"
            }
        ]
    }
}
```


## 4. 获取订单信息

### URL及请求方法
`GET /api/mobile/technician/order/${orderId}`

### 请求参数

| 参数名称 | 是否必须 | 说明 | 举例 |
| ------ | -------- | ---- | --- |
| orderId | 是 | 订单编号, URL点位符参数 | 1 |

示例: `GET /api/mobile/technician/order/1`


### 返回数据

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "id": 39,
        "orderNum": "20160303120243RKM4QB",
        "orderType": 0,
        "photo": null,
        "orderTime": null,
        "addTime": 1456977763165,
        "creatorType": 0,
        "creatorId": 0,
        "creatorName": null,
        "contactPhone": null,
        "positionLon": null,
        "positionLat": null,
        "remark": null,
        "mainTechId": 1,
        "secondTechId": 0,
        "status": "NEWLY_CREATED"
    }
}
```

## 5. 抢单
技师收到订单推送信息后, 抢取订单
### URL及请求方法
`POST /api/mobile/technician/order/takeup`

### 请求参数

| 参数名称 | 是否必须 | 说明 | 举例 |
| ------ | -------- | ---- | --- |
| orderId | 是 | 订单编号 | 1 |


### 返回数据

#### a.抢单成功
```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "id": 46,
        "orderNum": "20160303120243KFX8W3",
        "orderType": 0,
        "photo": null,
        "orderTime": null,
        "addTime": 1456977763720,
        "creatorType": 0,
        "creatorId": 0,
        "creatorName": null,
        "contactPhone": null,
        "positionLon": null,
        "positionLat": null,
        "remark": null,
        "mainTechId": 1,
        "secondTechId": 0,
        "status": "TAKEN_UP"
    }
}
```

#### b.已有人接单

```
{"result": false,
"message": "已有人接单",
"error": "ILLEGAL_OPERATION",
"data": null}
```

#### c.订单已取消

```
{"result": false,
"message": "订单已取消",
"error": "ILLEGAL_OPERATION",
"data": null}
```

## 6. 开始工作
技师抢单成功后或接受合作邀请后, 点选**开始工作**
### URL及请求方法
`POST /api/mobile/technician/order/start`

### 请求参数

| 参数名称 | 是否必须 | 说明 | 举例 |
| ------ | -------- | ---- | --- |
| orderId | 是 | 订单编号 | 1 |
| ignoreInvitation | 否 | 是否忽略已发出的尚未回复的合作邀请, 默认为false | false |

### 返回数据

#### a.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "id": 4,
        "orderId": 41,
        "technicianId": 1,
        "positionLon": null,
        "positionLat": null,
        "startTime": 1456977763488,
        "signinTime": null,
        "endTime": null,
        "beforePhotos": null,
        "afterPhotos": null,
        "payment": null,
        "workItems": null,
        "carSeat": 0
    }
}
```

data字段是一个施工单对象

#### b.你没有这个订单

```
{"result": false,
"message": "你没有这个订单",
"error": "ILLEGAL_OPERATION",
"data": null}
```

#### c.订单已取消

```
{"result": false,
"message": "订单已取消",
"error": "ILLEGAL_OPERATION",
"data": null}
```

#### d.订单已施工完成

```
{"result": false,
"message": "订单已施工完成",
"error": "ILLEGAL_OPERATION",
"data": null}
```

#### e.你邀请的合作人还未接受或拒绝邀请

```
{"result": false,
"message": "你邀请的合作人还未接受或拒绝邀请",
"error": "ILLEGAL_OPERATION",
"data": null}
```

## 7. 订单签到
技师到达施工位置后，点选**签到**
### URL及请求方法
`POST /api/mobile/technician/order/signIn`

### 请求参数

| 参数名称 | 是否必须 | 说明 | 举例 |
| ------ | -------- | ---- | --- |
| orderId | 是 | 订单编号 | 1 |
| positionLon | 是 | 签到位置经度 | 23.25478 |
| positionLat | 是 | 签到位置纬度 | 45.23145 |

### 返回数据

#### a.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": null
}
```

#### b.订单已取消

```
{"result": false,
"message": "订单已取消",
"error": "ILLEGAL_OPERATION",
"data": null}
```

#### c.系统没有你的施工单

```
{"result": false,
"message": "系统没有你的施工单, 请先点选\"开始工作\"",
"error": "ILLEGAL_OPERATION",
"data": null}
```

#### d.订单还未开始工作或已结束工作

```
{"result": false,
"message": "订单还未开始工作或已结束工作",
"error": "ILLEGAL_OPERATION",
"data": null}
```