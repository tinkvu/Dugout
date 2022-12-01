import os
from flask import *
from werkzeug.utils import secure_filename

app=Flask(__name__)
from src.connection import *

@app.route('/login' ,methods=['post'])
def login():
    un=request.form['uname']
    pwd=request.form['password']
    qry=" Select * from login where username=%s and password =%s"
    values=(un,pwd)
    res= select1(qry,values)
    if res is None:
        return jsonify({'task': "invalid"})
    elif res[3]=="user":
        id=res[0]
        return jsonify({'task': 'valid','id':str(id)})


@app.route('/UserRegistration' ,methods=['post'])
def UserRegistration():
    try:
        print(request.form)
        firstname = request.form['firstname']
        lastname = request.form['lastname']
        place = request.form['place']
        post = request.form['post']
        pin = request.form['pin']
        email = request.form['email']
        pnum = request.form['pnum']
        password = request.form['password']
        q="insert into login values(null,%s,%s,'user')"
        value=(pnum,password)
        lid = iud(q, value)
        qry = "INSERT INTO `user` VALUES(NULL, %s,%s,%s,%s,%s,%s,%s,%s)"
        value=(firstname,lastname,place,post,pin,email,pnum,str(lid))
        iud(qry,value)
        return jsonify({'task': 'success'})
    except Exception as e:
        print(e)
        return jsonify({'task': 'already exist'})

@app.route("/AddRating", methods=['post'])
def AddRating():
    userid = request.form['userid']
    rating = request.form['rating']
    turfid = request.form['turf_id']

    qry = "insert into rating values(null,%s,%s,%s)"
    val = (userid,turfid,rating)
    iud(qry, val)

    return jsonify({'task': 'success'})

@app.route("/AddComplaints", methods=['post'])
def AddComplaints():
    userid = request.form['userid']
    complaint = request.form['complaint']
    qry = "insert into complaint values(null,%s,%s,curdate(),'pending')"
    val = (userid,complaint)
    iud(qry, val)
    return jsonify({'task': 'success'})

@app.route("/ViewComplaints", methods=['post'])
def ViewComplaints():
    userid = request.form['lid']
    qry= "SELECT * FROM complaint WHERE user_id=%s"
    value= userid
    res=androidselectall(qry,value)
    return jsonify(res)

@app.route("/ViewTurfs", methods=['post'])
def ViewTurfs():
    latitude = request.form['lati']
    longitude = request.form['longi']
    qry= "SELECT `turf`.*, (3959 * ACOS ( COS ( RADIANS('"+latitude+"') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('"+longitude+"') ) + SIN ( RADIANS('"+latitude+"') ) * SIN( RADIANS( latitude ) ))) AS user_distance FROM `turf` HAVING user_distance  < 31.068"
    res=androidselectallnew(qry)
    print(res,"res============")
    return jsonify(res)

@app.route("/ViewAllTurfs", methods=['post'])
def ViewAllTurfs():
    qry= "SELECT * FROM turf"
    # val=res;
    res=androidselectallnew(qry)
    return jsonify(res)







@app.route("/TurfFacilities", methods=['post'])
def TurfFacilities():
    turfid= request.form['turf_id']
    qry= "SELECT * FROM facility WHERE turf_id=%s"
    value=turfid
    res=select1(qry,value)
    reslis=[]
    row_headers =['facility']



    if res is not None:
        ree=res[2].split(',')
        for i in ree:
           reslis .append(dict(zip(row_headers, [i])))

    return jsonify(reslis)

@app.route("/ViewGallery", methods=['post'])
def ViewGallery():
    turfid= request.form['turf_id']
    qry= "SELECT * FROM gallery WHERE turf_id=%s"
    value=turfid
    res=androidselectall(qry,value)
    return jsonify(res)


@app.route("/viewTurfBooking", methods=['post'])
def viewTurfBooking():

    userid = request.form['userid']
    qry = "SELECT `booking`.*,`turf`.`turf_name`,`turf`.`place`,`turf`.`latitude`,`turf`.`longitude`,DATEDIFF(`date`,CURDATE()) AS diff FROM `booking` JOIN `turf` ON `booking`.`turf_id`=`turf`.`login_id` WHERE `booking`.`user_id`=%s"
    value = (userid,)
    res=androidselectall(qry,value)
    return jsonify(res)

@app.route("/ViewRate", methods=['post'])
def ViewRate():
    dtl=["6:00-7:00","7:00-8:00","8:00-9:00","9:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00","15:00-16:00","16:00-17:00","17:00-18:00"]
    ntl=["18:00-19:00","19:00-20:00","20:00-21:00","21:00-22:00","22:00-23:00","23:00-00:00","00:00-1:00","1:00-2:00","2:00-3:00","3:00-4:00","4:00-5:00","5:00-6:00"]

    ftime=request.form['ftime']
    ttime=request.form['ttime']
    type=request.form['type']
    date=request.form['date']
    tid=request.form['tid']
    qry="SELECT * FROM `booking` WHERE `turf_id`=%s AND `date`=%s and ground_type=%s"
    val=(tid,date,type)
    res=selectall2(qry,val)

    fh=int(ftime.split(':')[0])
    th=int(ttime.split(':')[0])

    sloatlist=[]
    for i in range(0,(th-fh)):
        sloat=str(fh+i)+":00-"+str(fh+i+1)+":00"
        sloatlist.append(sloat)

    status="availabe"
    for i in res:
        if i[4] in sloatlist:
            status="na"
            break
    if status=='na':
        return jsonify({"task":"Not Available"})
    else:
        qry="SELECT `turf`.`turf_type`,`rateinfo`.`d_rate`,`n_rate` FROM `rateinfo` JOIN `turf` ON `turf`.`login_id`=`rateinfo`.`turf_id` WHERE `rateinfo`.`turf_id`=%s"
        val=tid
        res=select1(qry,val)

        typelist=res[0].split(',')
        drate=res[1].split(',')
        nrate=res[2].split(',')
        i=0
        for j in range(0,len(typelist)):
            if typelist[j]==type:
                i=j
                break

        drate=int(drate[i])
        nrate=int(nrate[i])
        trate=0
        for i in sloatlist:
            if i in dtl:
                trate=trate+drate
            else:
                trate=trate+nrate
        return jsonify({"task": "Total Rate Rs:"+str(trate)})

@app.route("/TurfBooking", methods=['post'])
def TurfBooking():
    ftime = request.form['ftime']
    print(ftime)
    ttime = request.form['ttime']
    print(ttime)
    type = request.form['type']
    print(type)
    date = request.form['date']
    print(date)
    tid = request.form['tid']
    print(tid)
    uid=request.form['uid']
    print(uid)


    fh = int(ftime.split(':')[0])
    th = int(ttime.split(':')[0])

    sloatlist = []
    for i in range(0, (th - fh)):
        sloat = str(fh + i) + ":00-" + str(fh + i + 1) + ":00"
        sloatlist.append(sloat)
    for i in sloatlist:
        qry="INSERT INTO `booking` VALUES(NULL,%s,%s,%s,%s,'pending',%s)"
        val=(tid,uid,date,sloat,type)
        iud(qry,val)
        return jsonify({'task': "Your booking is still pending! Wait for the Turf Owner confirmation."})


app.run(host="0.0.0.0", port=5000)