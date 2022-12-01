import os
from flask import *
from werkzeug.utils import secure_filename

app=Flask(__name__)
from src.connection import *
app.secret_key='abc'

@app.route('/')
def index():
    return render_template("index.html")



@app.route('/main')
def main():
    return render_template("login.html")


@app.route('/AdminViewRating')
def AdminViewRating():
    qry="SELECT AVG(`rating`.rating),`turf`.`turf_name`,`turf`.`login_id` FROM `turf` JOIN `rating` ON `turf`.`login_id`=`rating`.`turf_id` GROUP BY `turf`.turf_id"
    res=selectall(qry)
    return render_template("admin_view_rating.html",data=res)

@app.route('/TurfViewRating')
def TurfViewRating():
    id=session['lid']


    qry1="SELECT AVG(rating) FROM `rating` WHERE `rating`.`turf_id`=%s GROUP BY turf_id"
    value1=str(id)
    res1=select1(qry1,value1)


    qry="SELECT `rating`.`rating`,`user`.`f_name`,`user`.`l_name`,`user`.`email` FROM `user` JOIN `rating` ON `user`.`login_id`=`rating`.`user_id`  WHERE `rating`.`turf_id`=%s GROUP BY `rating`.`user_id`"

    value=str(id)
    res = selectall2(qry,value)
    return render_template("turf_view_rating.html",data=res,data1=res1)

@app.route('/adminhome')
def adminhome():
    return render_template("adminhome.html")


@app.route('/blockturf')
def blockturf():
    qry = "SELECT turf.* FROM turf JOIN login ON login.login_id=turf.login_id WHERE login.utype='turf'"
    res3 = selectall(qry)
    return render_template("blockturf.html", data3=res3)

@app.route('/blockt')
def blockt():
    id=request.args['id']
    qry="update login set utype='blocked' where login_id =%s "
    value=(str(id))
    iud(qry,value)
    return '''<script> alert("Blocked Successfully!");window.location="/blockturf";</script>'''

@app.route('/unblockturfs')
def unblockturfs():
    qry = "SELECT turf.* FROM turf JOIN login ON login.login_id=turf.login_id WHERE login.utype='blocked'"
    res4 = selectall(qry)
    return render_template("unblockedturf.html", data4=res4)

@app.route('/unblockt')
def unblockt():
    id=request.args['id']
    qry="update login set utype='turf' where login_id =%s "
    value=(str(id))
    iud(qry,value)
    return '''<script> alert("Unblocked Successfully!");window.location="/unblockturfs";</script>'''

@app.route('/approveturfowner')
def approveturfowner():
    qry="SELECT turf.* FROM turf JOIN login ON login.login_id=turf.login_id WHERE login.utype='pending'"
    res2=selectall(qry)
    return render_template("approveturfowner.html", data2=res2)


@app.route('/reg')
def reg():
    return render_template("reg.html")

@app.route('/registration')
def registration():
    return render_template("registration.html")

@app.route('/turfhome')
def turfhome():
    return render_template("Turf home.html")

@app.route('/reply')
def reply():
    id=request.args['id']
    session['cid']=id

    return render_template("reply.html")

@app.route('/accept')
def accept():
    id=request.args['id']
    qry="update login set utype='turf' where login_id =%s "
    value=(str(id))
    iud(qry,value)
    return '''<script> alert("accepted");window.location="/approveturfowner";</script>'''

#  @app.route('/reject')
# def reject():
#     id=request.args['id']
#     qry="update login set utype='rejected' where login_id =%s "
#     value=(str(id))
#     iud(qry,value)
#     return '''<script> alert("rejected");window.location="/approveturfowner";</script>'''



@app.route('/townerregistration')
def turfownerregistration():
    return render_template("turfownerregistration.html")

@app.route('/RegisterTurf', methods=['post'])
def RegisterTurf():
    try:
        turf_name=request.form['textfield']
        place=request.form['textfield2']
        post=request.form['textfield3']
        pin=request.form['textfield4']
        mob=request.form['textfield5']
        latitude=request.form['textfield6']
        longitude=request.form['textfield7']
        username=request.form['textfield8']
        password=request.form['textfield10']
        ttype=request.form.getlist('ttype')
        ttypes=','.join(ttype)

        qry="INSERT INTO login VALUES(NULL,%s,%s,'pending')"
        val=(username,password)
        lid=iud(qry,val)

        qry1="INSERT INTO turf VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        val=(turf_name,place,post,pin,mob,latitude,longitude,str(lid),ttypes)
        iud(qry1,val)
        return '''<script> alert("Sucessfully Registred Kindly Wait for Approval");window.location="/";</script>'''
    except Exception as e:
        return '''<script> alert("Already exist");window.location="/townerregistration#";</script>'''

@app.route('/ViewBookings')
def ViewBookings():
    qry="SELECT `booking`.`book_id`,`date`,`timeslot`,`user`.`f_name`,`l_name` ,`booking`.`status`,`user`.`pnum` FROM `booking` JOIN `user` ON `user`.`login_id`=`booking`.`user_id` AND turf_id=%s"
    val=(str(session['lid']),)
    res=selectall2(qry,val)
    return render_template("View BOOKING.html",val=res)


@app.route('/acceptbooking')
def acceptbooking():
    id=request.args.get('id')
    qry="UPDATE `booking` SET `status`='Accepted' WHERE `book_id`=%s"
    val=(id,)
    iud(qry,val)
    return redirect('/ViewBookings')


@app.route('/rejectbooking')
def rejectbooking():
    id=request.args.get('id')
    qry="UPDATE `booking` SET `status`='Rejected' WHERE `book_id`=%s"
    val=(id,)
    iud(qry,val)
    return redirect('/ViewBookings')


@app.route('/usercomplaint')
def usercomplaint():
    qry="SELECT `complaint`.*,`user`.* FROM `complaint` JOIN `user` ON user.login_id=complaint.user_id WHERE `complaint`.`reply`='pending' "
    comp=selectall(qry)
    return render_template("usercomplaint.html", data2=comp)


@app.route('/SendReply',methods=['post'])
def SendReply():
    print(session['cid'],"..............................")
    reply=request.form['reply']
    qry="UPDATE complaint SET reply=%s WHERE complaint_id=%s"
    print("UPDATE complaint SET reply=%s WHERE complaint_id=%s")
    value=(reply,str(session['cid']))
    print(reply,str(session['cid']))
    iud(qry,value)

    return '''<script> alert("Successfully replied!");window.location="/usercomplaint";</script>'''

@app.route('/viewfeatures1',methods=['post'])
def viewfeatures1():
    tid=request.form['select']
    print(tid,"==================")
    qry = "SELECT * FROM `turf`"
    res = selectall(qry)
    qry1="SELECT * FROM facility WHERE `turf_id`=%s"
    val=(tid,)
    ress=select1(qry1,val)
    print(ress)
    re=[]
    if ress is not None:
        re=ress[2].split(',')
    print(re,"===================")
    return render_template("viewfeatures.html", data1=res,data=re,s=str(tid))

@app.route('/viewfeatures')
def viewfeatures():
    qry = "SELECT * FROM `turf`"
    res = selectall(qry)
    return render_template("viewfeatures.html", data1=res)

@app.route('/ViewGallery')
def ViewGallery():
    lid = session['lid']
    qry = "SELECT * FROM `gallery` where turf_id=%s"
    res = selectall2(qry,str(lid))
    return render_template("View Gllery.html", data=res)

@app.route('/AddGllery', methods=['post'])
def AddGllery():
    return render_template("Add Gllery.html")

@app.route('/RemoveGI')
def RemoveGI():
    qry="DELETE FROM gallery WHERE gallery_id=%s"
    id = request.args.get('id')
    value=(str(id))
    iud(qry,value)
    return redirect('/ViewGallery')


@app.route('/AddGlleryImg', methods=['post'])
def AddGlleryImg():
    file=request.files['file']
    fn=secure_filename(file.filename)
    path=r"./static/fimg"
    file.save(os.path.join(path,fn))
    qry="INSERT INTO gallery VALUES(NULL,%s,%s)"
    val=(str(session['lid']),fn)
    iud(qry,val)
    return redirect('/ViewGallery')

@app.route('/AddNewFac')
def AddNewFac():
    qry="SELECT `facility` FROM `facility` WHERE `turf_id`=%s"
    val=str(session['lid'])
    res=select1(qry,val)
    ress=''
    if res is not None:
        ress=res[0]
    return render_template('addnewfac.html', v=ress)

@app.route('/AddNewFac1',methods=['post'])
def AddNewFac1():
    # fac=request.form['facility']
    # desc = request.form['description']
    facility = request.form.getlist('facility')
    facility = ','.join(facility)

    qry="select * from `facility` WHERE `turf_id`=%s"
    val=str(session['lid'])
    res=select1(qry,val)
    if res is None:
        qry="INSERT INTO `facility` VALUES(NULL, %s, %s)"
        value = (str(session['lid']),facility)
        iud(qry,value)
    else:
        qry = "UPDATE `facility` SET `facility`=%s WHERE `turf_id`=%s"
        value = (facility, str(session['lid']))
        iud(qry,value)
    return redirect('/AddNewFac')

# @app.route('/EditNewFac1',methods=['post'])
# def EditNewFac1():
#     id=request.form['id']
#     fac=request.form['facility']
#     desc = request.form['description']
#     qry="UPDATE `facility` SET `facility`=%s,`description`=%s WHERE `fac_id`=%s"
#     value=(fac,desc,id)
#     iud(qry,value)
#     return redirect('/ManageFacility')
# @app.route('/ManageFacility')
# def ManageFacility():
#     qry="SELECT * FROM facility WHERE turf_id=%s"
#     value=(str(session['lid']))
#     res=selectall2(qry,value)
#     return render_template("managefacilities.html",val=res)
#
# @app.route('/EditFacility')
# def EditFacility():
#     id = request.args.get('id')
#     qry = "select * from facility WHERE fac_id=%s"
#     val = (str(id),)
#     res=select1(qry, val)
#     return render_template('editnewfac.html',i=res)
# @app.route('/DeleteFacility')
# def DeleteFacility():
#     id=request.args.get('id')
#     qry="delete from facility WHERE fac_id=%s"
#     val=(str(id),)
#     iud(qry,val)
#     return redirect('/ManageFacility')


@app.route('/ViewRateInfo')
def ViewRateInfo():
    lid=session['lid']
    qry="SELECT `turf_type` FROM `turf` WHERE `login_id`=%s"
    res=select1(qry,str(lid))
    ttype=res[0].split(',')

    qry = "SELECT * FROM `rateinfo` WHERE turf_id=%s"
    res6 = select1(qry,str(lid))

    result=[]
    if res6 is not None:
        dayp = res6[2].split(',')
        nayp = res6[3].split(',')

        for i in range(0,len(ttype)):
            row=[ttype[i],dayp[i],nayp[i]]
            result.append(row)

    return render_template("View Rate Info.html", data6=result)


@app.route('/AddRateInfo')
def AddRateInfo():
    lid = session['lid']
    qry = "SELECT `turf_type` FROM `turf` WHERE `login_id`=%s"
    res = select1(qry, str(lid))
    ttype = res[0].split(',')

    qry = "SELECT * FROM `rateinfo` WHERE turf_id=%s"
    res6 = select1(qry, str(lid))

    result = []
    if res6 is not None:
        dayp = res6[2].split(',')
        nayp = res6[3].split(',')

        for i in range(0, len(ttype)):
            row = [ttype[i], dayp[i], nayp[i]]
            result.append(row)
    else:
        for i in range(0, len(ttype)):
            row = [ttype[i], '','']
            result.append(row)

    return render_template("Add Rate Info.html",val=result)

@app.route('/AddRateInfo1',methods=['post'])
def AddRateInfo1():
    lid = session['lid']
    qry = "SELECT `turf_type` FROM `turf` WHERE `login_id`=%s"
    res = select1(qry, str(lid))
    ttype = res[0].split(',')

    drate = []
    nrate = []

    for i in range(len(ttype)):
        dr = request.form['d' + ttype[i]]
        nr = request.form['n' + ttype[i]]
        drate.append(str(dr))
        nrate.append(str(nr))
    drate=','.join(drate)
    nrate=','.join(nrate)

    qry1="SELECT * FROM `rateinfo` WHERE `turf_id`=%s"
    val=(str(session['lid']),)
    res=select1(qry1,val)
    if res is None:
        qry="INSERT INTO `rateinfo` VALUES(NULL,%s,%s,%s) "
        val=(str(session['lid']),drate,nrate)
        iud(qry,val)
        return redirect('/ViewRateInfo')
    else:
        qry = "UPDATE `rateinfo` SET `d_rate`=%s,`n_rate`=%s WHERE `turf_id`=%s"
        val = ( drate, nrate,str(session['lid']))
        iud(qry, val)
        return redirect('/ViewRateInfo')




@app.route('/viewuser')
def viewuser():
    qry="select * from user"
    res=selectall(qry)

    return render_template("viewuser.html",data=res)


@app.route('/login' ,methods=['post'])
def login():
    un=request.form['uname']
    pwd=request.form['password']
    qry=" Select * from login where username= %s and password = %s"
    values=(un,pwd)
    res= select1(qry,values)
    if res is None:
        return '''<script> alert("Please enter genuine credentials!");window.location="/main";</script>'''
    elif res[3]=="admin":
        return render_template("adminhome.html")
    elif res[3]=="turf":
        session['lid']=str(res[0])
        return render_template("Turf home.html")
    elif res[3]=="blocked":
        return '''<script> alert("You have been blocked by Admins! Please contact our customer care!");window.location="/";</script>'''
    elif res[3] == "pending":
        return '''<script> alert("Please wait for approval! Or contact us if not accepted.");window.location="/";</script>'''
    else :
        return '''<script> alert("Please Login into our App!");window.location="/";</script>'''



app.run(debug=True)

