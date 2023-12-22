package com.example.apphkdn.ultil;

public class Server {
    public static String serverAddress = "http://192.168.31.183"; // Replace with your server address

    public static String linkCategory = serverAddress+"/server/getLsanpham.php";
    public static String linkNewProduct = serverAddress+"/server/getNewProduct.php";

    public static String linkReg = serverAddress+"/server/signup.php";

    public static String linkLog = serverAddress+"/server/login.php";

    public static String linkSendOTP = serverAddress+"/server/sendOTP.php";

    public static String linkLogOTP = serverAddress+"/server/LoginwithOtp.php";

    public static String linkGetProductByCategory = serverAddress+"/server/getProductByCategory.php?idcategory=";

    public static String linkGetProductBySearch = serverAddress+"/server/getProductBySearch.php?page=";

    public static String DetailProduct=serverAddress+"/server/GetProductByID.php?product_id=";

    public static String LinkRegistorSeller = serverAddress+"/server/RegisterSeller.php";
}
