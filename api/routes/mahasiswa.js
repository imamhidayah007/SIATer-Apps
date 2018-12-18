/**
 * Module dependencies.
 */

var jwt = require('jsonwebtoken');
var atob = require('atob');
var Cryptr = require('cryptr'),
cryptr = new Cryptr('myTotalySecretKey');


//---------------------------------------signup services---------------------------------------------------------

exports.register=function(req , res){
    var tahunAktif  = req.body.tahun_aktif;
    var programStudi= req.body.program_studi;
	var npm= req.body.npm;
    var pass= req.body.password;
    var email=req.body.email;
	var telepon=req.body.telepon;
	var angkatan=req.body.angkatan_s2;
    var dec_pass =atob(pass);
    var encrypted_pass = cryptr.encrypt(dec_pass);

    var cekMahasiswa = "select npm from tbl_mahasiswa WHERE `npm`='"+npm+"'";

    db.query(cekMahasiswa, function(err, results){

        if (results.length) {
            res.json({
                "results":
                    {"status": false, "msg" : 'NPM sudah terpakai'}
            });
            res.end();
        }else{
            var sql = "INSERT INTO `tbl_mahasiswa`(`id`, `tahun_aktif`, `program_studi`, `npm`, `password`, `email`, `telepon`, `angkatan_s2`) VALUES ('','" + tahunAktif + "','" + programStudi + "','" +npm+ "','" +encrypted_pass+ "','" +email+ "','" +telepon+ "','" +angkatan+ "')";

            var query = db.query(sql, function(err, result){
                if(result){
                    res.json({
                        "results":
                            {"status": true, "msg" : 'Berhasil Register'},
                        "npm" : npm
                    });
                    res.end();
                }else {
                    res.json({
                        "results":
                            {"status": false, "msg" : 'Gagal'}
                    });
                    res.end();
                }
            });
        }
    })
};
//---------------------------------------login services----------------------------------------------------------

exports.signin=function(req, res){
    var npm=req.body.npm;
    var pass= req.body.password;
    var dec_pass = atob(pass);
    var encrypted_pass = cryptr.encrypt(dec_pass);
    var sql="SELECT * FROM `tbl_mahasiswa` WHERE `npm`='"+npm+"' and password = '"+encrypted_pass+"'";

    db.query(sql, function(err, results){
        if(results != ""){
            var data = JSON.stringify(results);
            var secret = 'WOI';
            var now = Math.floor(Date.now() / 1000),
                iat = (now - 10),
                expiresIn = 3600,
                expr = (now + expiresIn),
                notBefore = (now - 10),
                jwtId = Math.random().toString(36).substring(7);
            var payload = {
                iat: iat,
                jwtid : jwtId,
                audience : 'TEST',
                data : data
            };

            jwt.sign(payload, secret, { algorithm: 'HS256', expiresIn : expiresIn}, function(err, token) {
                if(err){
                    res.json({
                        "results":
                            {
                                "status": false,
                                "msg" : 'Error occurred while generating token'
                            }
                    });
                } else {
                    if(token != false){
                        res.header();
                        res.json({
                            "results":
                                {"status": true,
                                    "token" : token,
                                    "user" : results[0],
                                    "msg" : "Berhasil Login"
                                }
                        });
                        res.end();
                    }
                    else{
                        res.json({
                            "results":
                                {"status": false,"msg" : 'Could not create token'},
                        });
                        res.end();
                    }

                }
            });
        }
        else if(results == ""){
            res.json({
                "results":
                    {"status": false, "msg" : 'Maaf User Tidak Di Temukan'}
            });
            res.end();
        }
    });
};