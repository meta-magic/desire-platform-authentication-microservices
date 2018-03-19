 CREATE TABLE "USERSCHEMA"
 (  
    "USERUUID" varchar(128) NOT NULL,
    "ACCOUNTEXPIRED" boolean NOT NULL,
    "ACCOUNTBLOCKED" integer NOT NULL,
    "CHANGEPASSWORD" boolean NOT NULL,
    "FIRSTTIMELOGIN" boolean NOT NULL,
    "USERTYPE" varchar(128) NOT NULL,
    "MTEID" varchar(128) NOT NULL,
    "ISACTIVE" boolean NOT NULL DEFAULT true,
    "VERSION" integer NOT NULL DEFAULT 1,
    "CREATEDBY" varchar(128) NOT NULL,
    "CREATEDTIME" timestamp with time zone NOT NULL,
    "UPDATEDBY" varchar(128) NOT NULL,
    "UPDATEDTIME" timestamp with time zone NOT NULL,
    CONSTRAINT USERSCHEMA_PK PRIMARY KEY ("USERUUID")
);
