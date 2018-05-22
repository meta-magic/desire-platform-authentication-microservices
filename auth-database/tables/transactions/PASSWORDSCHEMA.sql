CREATE TABLE "PASSWORDSCHEMA"
(
  "PASSWORDUUID" varchar(128) NOT NULL,
  "PASSWORDHASH" varchar(128) NOT NULL,
  "USERUUID" varchar(128) NOT NULL,
  "MTEID" varchar(128) NOT NULL,
  "ISACTIVE" boolean NOT NULL DEFAULT true,
  "VERSION" integer NOT NULL DEFAULT 1,
  "CREATEDBY" varchar(128) NOT NULL,
  "CREATEDTIME" timestamp with time zone NOT NULL,
  "UPDATEDBY" varchar(128) NOT NULL,
  "UPDATEDTIME" timestamp with time zone NOT NULL,
  CONSTRAINT "PASSWORDSCHEMA_PK" PRIMARY KEY ("PASSWORDUUID"),
  CONSTRAINT "PASSWORDSCHEMA_USERUUID_fkey" FOREIGN KEY ("USERUUID")
      REFERENCES "USERSCHEMA" ("USERUUID") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);