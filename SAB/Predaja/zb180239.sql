
CREATE TABLE [Admin]
( 
	[IdAdmin]            integer  NOT NULL 
)
go

CREATE TABLE [Adresa]
( 
	[IdAdresa]           integer  IDENTITY  NOT NULL ,
	[Ulica]              varchar(100)  NOT NULL ,
	[Broj]               integer  NOT NULL ,
	[IDGrad]             integer  NOT NULL ,
	[Xcord]              integer  NOT NULL ,
	[YCord]              integer  NOT NULL 
)
go

CREATE TABLE [Grad]
( 
	[Naziv]              varchar(100)  NOT NULL ,
	[PostanskiBroj]      varchar(100)  NULL ,
	[IDGrad]             integer  IDENTITY  NOT NULL 
)
go

CREATE TABLE [Korisnik]
( 
	[IdKorisnik]         integer  IDENTITY  NOT NULL ,
	[Ime]                varchar(100)  NOT NULL ,
	[Prezime]            varchar(100)  NOT NULL ,
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[Sifra]              varchar(100)  NOT NULL ,
	[IdAdresa]           integer  NOT NULL 
)
go

CREATE TABLE [Kupac]
( 
	[IdKupac]            integer  NOT NULL 
)
go

CREATE TABLE [Kurir]
( 
	[IdKurir]            integer  NOT NULL ,
	[BrojVozacke]        varchar(100)  NOT NULL ,
	[Status]             integer  NOT NULL 
	CONSTRAINT [Nula_430418619]
		 DEFAULT  0
	CONSTRAINT [StatusVozi_163705214]
		CHECK  ( Status BETWEEN 0 AND 1 ),
	[PROFIT]             decimal(10,3)  NOT NULL 
	CONSTRAINT [Nula_125401978]
		 DEFAULT  0,
	[Paketi]             integer  NOT NULL 
	CONSTRAINT [Nula_597206686]
		 DEFAULT  0,
	[IdVozilo]           integer  NULL 
)
go

CREATE TABLE [LokacijaMagacina]
( 
	[IDMag]              integer  IDENTITY  NOT NULL ,
	[IdAdresa]           integer  NOT NULL 
)
go

CREATE TABLE [Paket]
( 
	[IdPaket]            integer  IDENTITY  NOT NULL ,
	[TipPaketa]          integer  NOT NULL ,
	[TezinaPaketa]       decimal(10,3)  NOT NULL ,
	[StatusPaketa]       integer  NOT NULL 
	CONSTRAINT [Nula_466749348]
		 DEFAULT  0
	CONSTRAINT [StatusPaketa_905596366]
		CHECK  ( StatusPaketa BETWEEN 0 AND 4 ),
	[VremeKreiranjaZahteva] DATETIME2  NOT NULL ,
	[VremePrihvatanjaPonude] DATETIME2  NULL ,
	[ToAdresa]           integer  NOT NULL ,
	[FromAdresa]         integer  NOT NULL ,
	[Cena]               decimal(10,3)  NOT NULL ,
	[IdKorisnik]         integer  NOT NULL ,
	[DeoPlana]           integer  NULL 
)
go

CREATE TABLE [Parkiran]
( 
	[IDMag]              integer  NOT NULL ,
	[IdVozilo]           integer  NOT NULL 
)
go

CREATE TABLE [PlanVoznje]
( 
	[IdVoznja]           integer  NOT NULL ,
	[XCord]              integer  NULL ,
	[YCord]              integer  NULL ,
	[Stavka]             integer  NOT NULL ,
	[Tip]                integer  NULL ,
	[IdPaket]            integer  NULL 
)
go

CREATE TABLE [Pokupljeno]
( 
	[IdPaket]            integer  NOT NULL ,
	[IdVoznja]           integer  NOT NULL 
)
go

CREATE TABLE [UMagacinu]
( 
	[IDMag]              integer  NOT NULL ,
	[IdPaket]            integer  NOT NULL 
)
go

CREATE TABLE [Vozilo]
( 
	[RegBroj]            varchar(100)  NOT NULL ,
	[TipGoriva]          integer  NOT NULL 
	CONSTRAINT [Izmedju_1_i_3_Inkluzivno_757274139]
		CHECK  ( TipGoriva BETWEEN 0 AND 2 ),
	[Potrosnja]          decimal(10,3)  NOT NULL ,
	[Nosivost]           decimal(10,3)  NOT NULL ,
	[IdVozilo]           integer  IDENTITY  NOT NULL 
)
go

CREATE TABLE [Voznja]
( 
	[IdVoznja]           integer  IDENTITY  NOT NULL ,
	[IdVozilo]           integer  NOT NULL ,
	[XCord]              integer  NOT NULL ,
	[YCord]              integer  NOT NULL ,
	[Predjeno]           decimal(10,3)  NOT NULL 
	CONSTRAINT [Nula_2084830971]
		 DEFAULT  0,
	[IdKurir]            integer  NOT NULL ,
	[PocetniGrad]        integer  NULL ,
	[Profit]             decimal(10,3)  NULL ,
	[Stavka]             integer  NOT NULL 
)
go

CREATE TABLE [WorkingUser]
( 
	[IdWorkingUser]      integer  NOT NULL 
)
go

CREATE TABLE [Zahtev]
( 
	[IdZahtev]           integer  IDENTITY  NOT NULL ,
	[IdKorisnik]         integer  NOT NULL ,
	[BrojVozacke]        varchar(100)  NOT NULL 
)
go

ALTER TABLE [Admin]
	ADD CONSTRAINT [XPKAdmin] PRIMARY KEY  CLUSTERED ([IdAdmin] ASC)
go

ALTER TABLE [Adresa]
	ADD CONSTRAINT [XPKAdresa] PRIMARY KEY  CLUSTERED ([IdAdresa] ASC)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XPKGrad] PRIMARY KEY  CLUSTERED ([IDGrad] ASC)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XAK1Grad] UNIQUE ([PostanskiBroj]  ASC)
go

ALTER TABLE [Korisnik]
	ADD CONSTRAINT [XPKKorisnik] PRIMARY KEY  CLUSTERED ([IdKorisnik] ASC)
go

ALTER TABLE [Korisnik]
	ADD CONSTRAINT [XAK1KorisnickoIme] UNIQUE ([KorisnickoIme]  ASC)
go

ALTER TABLE [Kupac]
	ADD CONSTRAINT [XPKKupac] PRIMARY KEY  CLUSTERED ([IdKupac] ASC)
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [XPKKurir] PRIMARY KEY  CLUSTERED ([IdKurir] ASC)
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [BrojVozackeUnq] UNIQUE ([BrojVozacke]  ASC)
go

CREATE UNIQUE NONCLUSTERED INDEX [Vozilo] ON [Kurir]
( 
	[IdVozilo]            ASC
)
WHERE IdVozilo is not NULL
go

ALTER TABLE [LokacijaMagacina]
	ADD CONSTRAINT [XPKLokacijaMagacina] PRIMARY KEY  CLUSTERED ([IDMag] ASC)
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [XPKPaket] PRIMARY KEY  CLUSTERED ([IdPaket] ASC)
go

ALTER TABLE [Parkiran]
	ADD CONSTRAINT [XPKParkiran] PRIMARY KEY  CLUSTERED ([IDMag] ASC,[IdVozilo] ASC)
go

ALTER TABLE [Parkiran]
	ADD CONSTRAINT [IdVoziloUnq] UNIQUE ([IdVozilo]  ASC)
go

ALTER TABLE [PlanVoznje]
	ADD CONSTRAINT [XPKPlanVoznje] PRIMARY KEY  CLUSTERED ([IdVoznja] ASC,[Stavka] ASC)
go

ALTER TABLE [Pokupljeno]
	ADD CONSTRAINT [XPKPokupljeno] PRIMARY KEY  CLUSTERED ([IdPaket] ASC,[IdVoznja] ASC)
go

ALTER TABLE [UMagacinu]
	ADD CONSTRAINT [XPKUMagacinu] PRIMARY KEY  CLUSTERED ([IDMag] ASC,[IdPaket] ASC)
go

ALTER TABLE [Vozilo]
	ADD CONSTRAINT [XPKVozilo] PRIMARY KEY  CLUSTERED ([IdVozilo] ASC)
go

ALTER TABLE [Vozilo]
	ADD CONSTRAINT [RegBrojUnq] UNIQUE ([RegBroj]  ASC)
go

ALTER TABLE [Voznja]
	ADD CONSTRAINT [XPKVoznja] PRIMARY KEY  CLUSTERED ([IdVoznja] ASC)
go

ALTER TABLE [Voznja]
	ADD CONSTRAINT [UnqVozac] UNIQUE ([IdKurir]  ASC)
go

ALTER TABLE [Voznja]
	ADD CONSTRAINT [IdVoziloUnqVoznja] UNIQUE ([IdVozilo]  ASC)
go

ALTER TABLE [WorkingUser]
	ADD CONSTRAINT [XPKWorkingUser] PRIMARY KEY  CLUSTERED ([IdWorkingUser] ASC)
go

ALTER TABLE [Zahtev]
	ADD CONSTRAINT [XPKZahtev] PRIMARY KEY  CLUSTERED ([IdZahtev] ASC)
go

ALTER TABLE [Zahtev]
	ADD CONSTRAINT [BrojVozackeUnqZahtev] UNIQUE ([BrojVozacke]  ASC)
go


ALTER TABLE [Admin]
	ADD CONSTRAINT [R_7] FOREIGN KEY ([IdAdmin]) REFERENCES [WorkingUser]([IdWorkingUser])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Adresa]
	ADD CONSTRAINT [R_1] FOREIGN KEY ([IDGrad]) REFERENCES [Grad]([IDGrad])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Korisnik]
	ADD CONSTRAINT [R_3] FOREIGN KEY ([IdAdresa]) REFERENCES [Adresa]([IdAdresa])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Kupac]
	ADD CONSTRAINT [R_4] FOREIGN KEY ([IdKupac]) REFERENCES [Korisnik]([IdKorisnik])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_6] FOREIGN KEY ([IdKurir]) REFERENCES [WorkingUser]([IdWorkingUser])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_28] FOREIGN KEY ([IdVozilo]) REFERENCES [Vozilo]([IdVozilo])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [LokacijaMagacina]
	ADD CONSTRAINT [R_2] FOREIGN KEY ([IdAdresa]) REFERENCES [Adresa]([IdAdresa])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Paket]
	ADD CONSTRAINT [R_21] FOREIGN KEY ([FromAdresa]) REFERENCES [Adresa]([IdAdresa])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [R_22] FOREIGN KEY ([ToAdresa]) REFERENCES [Adresa]([IdAdresa])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [R_26] FOREIGN KEY ([IdKorisnik]) REFERENCES [Korisnik]([IdKorisnik])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Parkiran]
	ADD CONSTRAINT [R_29] FOREIGN KEY ([IDMag]) REFERENCES [LokacijaMagacina]([IDMag])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Parkiran]
	ADD CONSTRAINT [R_30] FOREIGN KEY ([IdVozilo]) REFERENCES [Vozilo]([IdVozilo])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [PlanVoznje]
	ADD CONSTRAINT [R_33] FOREIGN KEY ([IdVoznja]) REFERENCES [Voznja]([IdVoznja])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [PlanVoznje]
	ADD CONSTRAINT [R_34] FOREIGN KEY ([IdPaket]) REFERENCES [Paket]([IdPaket])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Pokupljeno]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([IdPaket]) REFERENCES [Paket]([IdPaket])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Pokupljeno]
	ADD CONSTRAINT [R_17] FOREIGN KEY ([IdVoznja]) REFERENCES [Voznja]([IdVoznja])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [UMagacinu]
	ADD CONSTRAINT [R_23] FOREIGN KEY ([IDMag]) REFERENCES [LokacijaMagacina]([IDMag])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [UMagacinu]
	ADD CONSTRAINT [R_24] FOREIGN KEY ([IdPaket]) REFERENCES [Paket]([IdPaket])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Voznja]
	ADD CONSTRAINT [R_13] FOREIGN KEY ([IdVozilo]) REFERENCES [Vozilo]([IdVozilo])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Voznja]
	ADD CONSTRAINT [R_27] FOREIGN KEY ([IdKurir]) REFERENCES [Kurir]([IdKurir])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Voznja]
	ADD CONSTRAINT [R_31] FOREIGN KEY ([PocetniGrad]) REFERENCES [Grad]([IDGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [WorkingUser]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([IdWorkingUser]) REFERENCES [Korisnik]([IdKorisnik])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Zahtev]
	ADD CONSTRAINT [R_10] FOREIGN KEY ([IdKorisnik]) REFERENCES [Korisnik]([IdKorisnik])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


CREATE TRIGGER tU_Admin ON Admin FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Admin */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdAdmin integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* WorkingUser  Admin on child update no action */
  /* ERWIN_RELATION:CHECKSUM="000176c4", PARENT_OWNER="", PARENT_TABLE="WorkingUser"
    CHILD_OWNER="", CHILD_TABLE="Admin"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="IdAdmin" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdAdmin)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,WorkingUser
        WHERE
          /* %JoinFKPK(inserted,WorkingUser) */
          inserted.IdAdmin = WorkingUser.IdWorkingUser
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Admin because WorkingUser does not exist.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Adresa ON Adresa FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on Adresa */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* Adresa  Paket on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0003bf7e", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Paket"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_22", FK_COLUMNS="ToAdresa" */
    IF EXISTS (
      SELECT * FROM deleted,Paket
      WHERE
        /*  %JoinFKPK(Paket,deleted," = "," AND") */
        Paket.ToAdresa = deleted.IdAdresa
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Adresa because Paket exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Adresa  Paket on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Paket"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_21", FK_COLUMNS="FromAdresa" */
    IF EXISTS (
      SELECT * FROM deleted,Paket
      WHERE
        /*  %JoinFKPK(Paket,deleted," = "," AND") */
        Paket.FromAdresa = deleted.IdAdresa
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Adresa because Paket exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Adresa  Korisnik on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Korisnik"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_3", FK_COLUMNS="IdAdresa" */
    IF EXISTS (
      SELECT * FROM deleted,Korisnik
      WHERE
        /*  %JoinFKPK(Korisnik,deleted," = "," AND") */
        Korisnik.IdAdresa = deleted.IdAdresa
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Adresa because Korisnik exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Adresa  LokacijaMagacina on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="LokacijaMagacina"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_2", FK_COLUMNS="IdAdresa" */
    IF EXISTS (
      SELECT * FROM deleted,LokacijaMagacina
      WHERE
        /*  %JoinFKPK(LokacijaMagacina,deleted," = "," AND") */
        LokacijaMagacina.IdAdresa = deleted.IdAdresa
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Adresa because LokacijaMagacina exists.'
      GOTO error
    END


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Adresa ON Adresa FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Adresa */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdAdresa integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Adresa  Paket on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0004ccf0", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Paket"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_22", FK_COLUMNS="ToAdresa" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdAdresa)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Paket
      WHERE
        /*  %JoinFKPK(Paket,deleted," = "," AND") */
        Paket.ToAdresa = deleted.IdAdresa
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Adresa because Paket exists.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Adresa  Paket on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Paket"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_21", FK_COLUMNS="FromAdresa" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdAdresa)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Paket
      WHERE
        /*  %JoinFKPK(Paket,deleted," = "," AND") */
        Paket.FromAdresa = deleted.IdAdresa
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Adresa because Paket exists.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Adresa  Korisnik on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Korisnik"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_3", FK_COLUMNS="IdAdresa" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdAdresa)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdAdresa = inserted.IdAdresa
        FROM inserted
      UPDATE Korisnik
      SET
        /*  %JoinFKPK(Korisnik,@ins," = ",",") */
        Korisnik.IdAdresa = @insIdAdresa
      FROM Korisnik,inserted,deleted
      WHERE
        /*  %JoinFKPK(Korisnik,deleted," = "," AND") */
        Korisnik.IdAdresa = deleted.IdAdresa
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Adresa update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Adresa  LokacijaMagacina on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="LokacijaMagacina"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_2", FK_COLUMNS="IdAdresa" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdAdresa)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdAdresa = inserted.IdAdresa
        FROM inserted
      UPDATE LokacijaMagacina
      SET
        /*  %JoinFKPK(LokacijaMagacina,@ins," = ",",") */
        LokacijaMagacina.IdAdresa = @insIdAdresa
      FROM LokacijaMagacina,inserted,deleted
      WHERE
        /*  %JoinFKPK(LokacijaMagacina,deleted," = "," AND") */
        LokacijaMagacina.IdAdresa = deleted.IdAdresa
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Adresa update because more than one row has been affected.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Grad ON Grad FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on Grad */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* Grad  Voznja on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0001daae", PARENT_OWNER="", PARENT_TABLE="Grad"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_31", FK_COLUMNS="PocetniGrad" */
    IF EXISTS (
      SELECT * FROM deleted,Voznja
      WHERE
        /*  %JoinFKPK(Voznja,deleted," = "," AND") */
        Voznja.PocetniGrad = deleted.IDGrad
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Grad because Voznja exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Grad  Adresa on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Grad"
    CHILD_OWNER="", CHILD_TABLE="Adresa"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="IDGrad" */
    IF EXISTS (
      SELECT * FROM deleted,Adresa
      WHERE
        /*  %JoinFKPK(Adresa,deleted," = "," AND") */
        Adresa.IDGrad = deleted.IDGrad
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Grad because Adresa exists.'
      GOTO error
    END


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Grad ON Grad FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Grad */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIDGrad integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Grad  Voznja on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="000271d2", PARENT_OWNER="", PARENT_TABLE="Grad"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_31", FK_COLUMNS="PocetniGrad" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IDGrad)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Voznja
      WHERE
        /*  %JoinFKPK(Voznja,deleted," = "," AND") */
        Voznja.PocetniGrad = deleted.IDGrad
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Grad because Voznja exists.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Grad  Adresa on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Grad"
    CHILD_OWNER="", CHILD_TABLE="Adresa"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_1", FK_COLUMNS="IDGrad" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IDGrad)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIDGrad = inserted.IDGrad
        FROM inserted
      UPDATE Adresa
      SET
        /*  %JoinFKPK(Adresa,@ins," = ",",") */
        Adresa.IDGrad = @insIDGrad
      FROM Adresa,inserted,deleted
      WHERE
        /*  %JoinFKPK(Adresa,deleted," = "," AND") */
        Adresa.IDGrad = deleted.IDGrad
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Grad update because more than one row has been affected.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Korisnik ON Korisnik FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on Korisnik */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* Korisnik  Paket on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00035b55", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="Paket"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_26", FK_COLUMNS="IdKorisnik" */
    IF EXISTS (
      SELECT * FROM deleted,Paket
      WHERE
        /*  %JoinFKPK(Paket,deleted," = "," AND") */
        Paket.IdKorisnik = deleted.IdKorisnik
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Korisnik because Paket exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Korisnik  Zahtev on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="Zahtev"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_10", FK_COLUMNS="IdKorisnik" */
    IF EXISTS (
      SELECT * FROM deleted,Zahtev
      WHERE
        /*  %JoinFKPK(Zahtev,deleted," = "," AND") */
        Zahtev.IdKorisnik = deleted.IdKorisnik
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Korisnik because Zahtev exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Korisnik  WorkingUser on parent delete cascade */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="WorkingUser"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="IdWorkingUser" */
    DELETE WorkingUser
      FROM WorkingUser,deleted
      WHERE
        /*  %JoinFKPK(WorkingUser,deleted," = "," AND") */
        WorkingUser.IdWorkingUser = deleted.IdKorisnik

    /* erwin Builtin Trigger */
    /* Korisnik  Kupac on parent delete cascade */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="Kupac"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_4", FK_COLUMNS="IdKupac" */
    DELETE Kupac
      FROM Kupac,deleted
      WHERE
        /*  %JoinFKPK(Kupac,deleted," = "," AND") */
        Kupac.IdKupac = deleted.IdKorisnik


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Korisnik ON Korisnik FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Korisnik */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdKorisnik integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Korisnik  Paket on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00061d6b", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="Paket"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_26", FK_COLUMNS="IdKorisnik" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdKorisnik)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Paket
      WHERE
        /*  %JoinFKPK(Paket,deleted," = "," AND") */
        Paket.IdKorisnik = deleted.IdKorisnik
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Korisnik because Paket exists.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Korisnik  Zahtev on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="Zahtev"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_10", FK_COLUMNS="IdKorisnik" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdKorisnik)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdKorisnik = inserted.IdKorisnik
        FROM inserted
      UPDATE Zahtev
      SET
        /*  %JoinFKPK(Zahtev,@ins," = ",",") */
        Zahtev.IdKorisnik = @insIdKorisnik
      FROM Zahtev,inserted,deleted
      WHERE
        /*  %JoinFKPK(Zahtev,deleted," = "," AND") */
        Zahtev.IdKorisnik = deleted.IdKorisnik
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Korisnik update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Korisnik  WorkingUser on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="WorkingUser"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="IdWorkingUser" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdKorisnik)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdKorisnik = inserted.IdKorisnik
        FROM inserted
      UPDATE WorkingUser
      SET
        /*  %JoinFKPK(WorkingUser,@ins," = ",",") */
        WorkingUser.IdWorkingUser = @insIdKorisnik
      FROM WorkingUser,inserted,deleted
      WHERE
        /*  %JoinFKPK(WorkingUser,deleted," = "," AND") */
        WorkingUser.IdWorkingUser = deleted.IdKorisnik
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Korisnik update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Korisnik  Kupac on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="Kupac"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_4", FK_COLUMNS="IdKupac" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdKorisnik)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdKorisnik = inserted.IdKorisnik
        FROM inserted
      UPDATE Kupac
      SET
        /*  %JoinFKPK(Kupac,@ins," = ",",") */
        Kupac.IdKupac = @insIdKorisnik
      FROM Kupac,inserted,deleted
      WHERE
        /*  %JoinFKPK(Kupac,deleted," = "," AND") */
        Kupac.IdKupac = deleted.IdKorisnik
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Korisnik update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Adresa  Korisnik on child update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Korisnik"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_3", FK_COLUMNS="IdAdresa" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdAdresa)
  BEGIN
    INSERT Adresa (IdAdresa)
      SELECT IdAdresa
      FROM   inserted
      WHERE
        /* %NotnullFK(inserted," IS NOT NULL AND") */
        
        NOT EXISTS (
          SELECT * FROM Adresa
          WHERE
            /* %JoinFKPK(inserted,Adresa," = "," AND") */
            inserted.IdAdresa = Adresa.IdAdresa
        )
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tU_Kupac ON Kupac FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Kupac */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdKupac integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Korisnik  Kupac on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00016312", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="Kupac"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_4", FK_COLUMNS="IdKupac" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdKupac)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Korisnik
        WHERE
          /* %JoinFKPK(inserted,Korisnik) */
          inserted.IdKupac = Korisnik.IdKorisnik
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Kupac because Korisnik does not exist.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Kurir ON Kurir FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on Kurir */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* Kurir  Voznja on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0000ffb5", PARENT_OWNER="", PARENT_TABLE="Kurir"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="IdKurir" */
    IF EXISTS (
      SELECT * FROM deleted,Voznja
      WHERE
        /*  %JoinFKPK(Voznja,deleted," = "," AND") */
        Voznja.IdKurir = deleted.IdKurir
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Kurir because Voznja exists.'
      GOTO error
    END


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Kurir ON Kurir FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Kurir */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdKurir integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Kurir  Voznja on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00026401", PARENT_OWNER="", PARENT_TABLE="Kurir"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="IdKurir" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdKurir)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,Voznja
      WHERE
        /*  %JoinFKPK(Voznja,deleted," = "," AND") */
        Voznja.IdKurir = deleted.IdKurir
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update Kurir because Voznja exists.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* WorkingUser  Kurir on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="WorkingUser"
    CHILD_OWNER="", CHILD_TABLE="Kurir"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_6", FK_COLUMNS="IdKurir" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdKurir)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,WorkingUser
        WHERE
          /* %JoinFKPK(inserted,WorkingUser) */
          inserted.IdKurir = WorkingUser.IdWorkingUser
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Kurir because WorkingUser does not exist.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_LokacijaMagacina ON LokacijaMagacina FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on LokacijaMagacina */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* LokacijaMagacina  Parkiran on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00020e65", PARENT_OWNER="", PARENT_TABLE="LokacijaMagacina"
    CHILD_OWNER="", CHILD_TABLE="Parkiran"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_29", FK_COLUMNS="IDMag" */
    IF EXISTS (
      SELECT * FROM deleted,Parkiran
      WHERE
        /*  %JoinFKPK(Parkiran,deleted," = "," AND") */
        Parkiran.IDMag = deleted.IDMag
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete LokacijaMagacina because Parkiran exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* LokacijaMagacina  UMagacinu on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="LokacijaMagacina"
    CHILD_OWNER="", CHILD_TABLE="UMagacinu"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_23", FK_COLUMNS="IDMag" */
    IF EXISTS (
      SELECT * FROM deleted,UMagacinu
      WHERE
        /*  %JoinFKPK(UMagacinu,deleted," = "," AND") */
        UMagacinu.IDMag = deleted.IDMag
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete LokacijaMagacina because UMagacinu exists.'
      GOTO error
    END


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_LokacijaMagacina ON LokacijaMagacina FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on LokacijaMagacina */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIDMag integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* LokacijaMagacina  Parkiran on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="0002df78", PARENT_OWNER="", PARENT_TABLE="LokacijaMagacina"
    CHILD_OWNER="", CHILD_TABLE="Parkiran"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_29", FK_COLUMNS="IDMag" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IDMag)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIDMag = inserted.IDMag
        FROM inserted
      UPDATE Parkiran
      SET
        /*  %JoinFKPK(Parkiran,@ins," = ",",") */
        Parkiran.IDMag = @insIDMag
      FROM Parkiran,inserted,deleted
      WHERE
        /*  %JoinFKPK(Parkiran,deleted," = "," AND") */
        Parkiran.IDMag = deleted.IDMag
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade LokacijaMagacina update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* LokacijaMagacina  UMagacinu on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="LokacijaMagacina"
    CHILD_OWNER="", CHILD_TABLE="UMagacinu"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_23", FK_COLUMNS="IDMag" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IDMag)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIDMag = inserted.IDMag
        FROM inserted
      UPDATE UMagacinu
      SET
        /*  %JoinFKPK(UMagacinu,@ins," = ",",") */
        UMagacinu.IDMag = @insIDMag
      FROM UMagacinu,inserted,deleted
      WHERE
        /*  %JoinFKPK(UMagacinu,deleted," = "," AND") */
        UMagacinu.IDMag = deleted.IDMag
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade LokacijaMagacina update because more than one row has been affected.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Paket ON Paket FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on Paket */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* Paket  PlanVoznje on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0002f013", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="PlanVoznje"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_34", FK_COLUMNS="IdPaket" */
    IF EXISTS (
      SELECT * FROM deleted,PlanVoznje
      WHERE
        /*  %JoinFKPK(PlanVoznje,deleted," = "," AND") */
        PlanVoznje.IdPaket = deleted.IdPaket
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Paket because PlanVoznje exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Paket  UMagacinu on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="UMagacinu"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_24", FK_COLUMNS="IdPaket" */
    IF EXISTS (
      SELECT * FROM deleted,UMagacinu
      WHERE
        /*  %JoinFKPK(UMagacinu,deleted," = "," AND") */
        UMagacinu.IdPaket = deleted.IdPaket
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Paket because UMagacinu exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Paket  Pokupljeno on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="Pokupljeno"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_16", FK_COLUMNS="IdPaket" */
    IF EXISTS (
      SELECT * FROM deleted,Pokupljeno
      WHERE
        /*  %JoinFKPK(Pokupljeno,deleted," = "," AND") */
        Pokupljeno.IdPaket = deleted.IdPaket
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Paket because Pokupljeno exists.'
      GOTO error
    END


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Paket ON Paket FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Paket */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdPaket integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Paket  PlanVoznje on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00069c08", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="PlanVoznje"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_34", FK_COLUMNS="IdPaket" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdPaket)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdPaket = inserted.IdPaket
        FROM inserted
      UPDATE PlanVoznje
      SET
        /*  %JoinFKPK(PlanVoznje,@ins," = ",",") */
        PlanVoznje.IdPaket = @insIdPaket
      FROM PlanVoznje,inserted,deleted
      WHERE
        /*  %JoinFKPK(PlanVoznje,deleted," = "," AND") */
        PlanVoznje.IdPaket = deleted.IdPaket
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Paket update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Paket  UMagacinu on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="UMagacinu"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_24", FK_COLUMNS="IdPaket" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdPaket)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdPaket = inserted.IdPaket
        FROM inserted
      UPDATE UMagacinu
      SET
        /*  %JoinFKPK(UMagacinu,@ins," = ",",") */
        UMagacinu.IdPaket = @insIdPaket
      FROM UMagacinu,inserted,deleted
      WHERE
        /*  %JoinFKPK(UMagacinu,deleted," = "," AND") */
        UMagacinu.IdPaket = deleted.IdPaket
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Paket update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Paket  Pokupljeno on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="Pokupljeno"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_16", FK_COLUMNS="IdPaket" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdPaket)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdPaket = inserted.IdPaket
        FROM inserted
      UPDATE Pokupljeno
      SET
        /*  %JoinFKPK(Pokupljeno,@ins," = ",",") */
        Pokupljeno.IdPaket = @insIdPaket
      FROM Pokupljeno,inserted,deleted
      WHERE
        /*  %JoinFKPK(Pokupljeno,deleted," = "," AND") */
        Pokupljeno.IdPaket = deleted.IdPaket
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Paket update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Adresa  Paket on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Paket"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_22", FK_COLUMNS="ToAdresa" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(ToAdresa)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Adresa
        WHERE
          /* %JoinFKPK(inserted,Adresa) */
          inserted.ToAdresa = Adresa.IdAdresa
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Paket because Adresa does not exist.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Adresa  Paket on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Adresa"
    CHILD_OWNER="", CHILD_TABLE="Paket"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_21", FK_COLUMNS="FromAdresa" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(FromAdresa)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Adresa
        WHERE
          /* %JoinFKPK(inserted,Adresa) */
          inserted.FromAdresa = Adresa.IdAdresa
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Paket because Adresa does not exist.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tU_PlanVoznje ON PlanVoznje FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on PlanVoznje */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdVoznja integer, 
           @insStavka integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Paket  PlanVoznje on child update no action */
  /* ERWIN_RELATION:CHECKSUM="0002bdbe", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="PlanVoznje"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_34", FK_COLUMNS="IdPaket" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdPaket)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Paket
        WHERE
          /* %JoinFKPK(inserted,Paket) */
          inserted.IdPaket = Paket.IdPaket
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    select @nullcnt = count(*) from inserted where
      inserted.IdPaket IS NULL
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update PlanVoznje because Paket does not exist.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Voznja  PlanVoznje on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Voznja"
    CHILD_OWNER="", CHILD_TABLE="PlanVoznje"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="IdVoznja" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdVoznja)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Voznja
        WHERE
          /* %JoinFKPK(inserted,Voznja) */
          inserted.IdVoznja = Voznja.IdVoznja
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update PlanVoznje because Voznja does not exist.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tU_Pokupljeno ON Pokupljeno FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Pokupljeno */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdPaket integer, 
           @insIdVoznja integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Voznja  Pokupljeno on child update no action */
  /* ERWIN_RELATION:CHECKSUM="0002ab10", PARENT_OWNER="", PARENT_TABLE="Voznja"
    CHILD_OWNER="", CHILD_TABLE="Pokupljeno"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_17", FK_COLUMNS="IdVoznja" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdVoznja)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Voznja
        WHERE
          /* %JoinFKPK(inserted,Voznja) */
          inserted.IdVoznja = Voznja.IdVoznja
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Pokupljeno because Voznja does not exist.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Paket  Pokupljeno on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="Pokupljeno"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_16", FK_COLUMNS="IdPaket" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdPaket)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Paket
        WHERE
          /* %JoinFKPK(inserted,Paket) */
          inserted.IdPaket = Paket.IdPaket
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Pokupljeno because Paket does not exist.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tU_UMagacinu ON UMagacinu FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on UMagacinu */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIDMag integer, 
           @insIdPaket integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Paket  UMagacinu on child update cascade */
  /* ERWIN_RELATION:CHECKSUM="00021e20", PARENT_OWNER="", PARENT_TABLE="Paket"
    CHILD_OWNER="", CHILD_TABLE="UMagacinu"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_24", FK_COLUMNS="IdPaket" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdPaket)
  BEGIN
    INSERT Paket (IdPaket)
      SELECT IdPaket
      FROM   inserted
      WHERE
        /* %NotnullFK(inserted," IS NOT NULL AND") */
        
        NOT EXISTS (
          SELECT * FROM Paket
          WHERE
            /* %JoinFKPK(inserted,Paket," = "," AND") */
            inserted.IdPaket = Paket.IdPaket
        )
  END

  /* erwin Builtin Trigger */
  /* LokacijaMagacina  UMagacinu on child update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="LokacijaMagacina"
    CHILD_OWNER="", CHILD_TABLE="UMagacinu"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_23", FK_COLUMNS="IDMag" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IDMag)
  BEGIN
    INSERT LokacijaMagacina (IDMag)
      SELECT IDMag
      FROM   inserted
      WHERE
        /* %NotnullFK(inserted," IS NOT NULL AND") */
        
        NOT EXISTS (
          SELECT * FROM LokacijaMagacina
          WHERE
            /* %JoinFKPK(inserted,LokacijaMagacina," = "," AND") */
            inserted.IDMag = LokacijaMagacina.IDMag
        )
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Vozilo ON Vozilo FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on Vozilo */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* Vozilo  Parkiran on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0002c5ae", PARENT_OWNER="", PARENT_TABLE="Vozilo"
    CHILD_OWNER="", CHILD_TABLE="Parkiran"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_30", FK_COLUMNS="IdVozilo" */
    IF EXISTS (
      SELECT * FROM deleted,Parkiran
      WHERE
        /*  %JoinFKPK(Parkiran,deleted," = "," AND") */
        Parkiran.IdVozilo = deleted.IdVozilo
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Vozilo because Parkiran exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Vozilo  Kurir on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Vozilo"
    CHILD_OWNER="", CHILD_TABLE="Kurir"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="IdVozilo" */
    IF EXISTS (
      SELECT * FROM deleted,Kurir
      WHERE
        /*  %JoinFKPK(Kurir,deleted," = "," AND") */
        Kurir.IdVozilo = deleted.IdVozilo
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Vozilo because Kurir exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Vozilo  Voznja on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Vozilo"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_13", FK_COLUMNS="IdVozilo" */
    IF EXISTS (
      SELECT * FROM deleted,Voznja
      WHERE
        /*  %JoinFKPK(Voznja,deleted," = "," AND") */
        Voznja.IdVozilo = deleted.IdVozilo
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Vozilo because Voznja exists.'
      GOTO error
    END


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Vozilo ON Vozilo FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Vozilo */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdVozilo integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Vozilo  Parkiran on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="0003ff02", PARENT_OWNER="", PARENT_TABLE="Vozilo"
    CHILD_OWNER="", CHILD_TABLE="Parkiran"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_30", FK_COLUMNS="IdVozilo" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdVozilo)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdVozilo = inserted.IdVozilo
        FROM inserted
      UPDATE Parkiran
      SET
        /*  %JoinFKPK(Parkiran,@ins," = ",",") */
        Parkiran.IdVozilo = @insIdVozilo
      FROM Parkiran,inserted,deleted
      WHERE
        /*  %JoinFKPK(Parkiran,deleted," = "," AND") */
        Parkiran.IdVozilo = deleted.IdVozilo
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Vozilo update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Vozilo  Kurir on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Vozilo"
    CHILD_OWNER="", CHILD_TABLE="Kurir"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_28", FK_COLUMNS="IdVozilo" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdVozilo)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdVozilo = inserted.IdVozilo
        FROM inserted
      UPDATE Kurir
      SET
        /*  %JoinFKPK(Kurir,@ins," = ",",") */
        Kurir.IdVozilo = @insIdVozilo
      FROM Kurir,inserted,deleted
      WHERE
        /*  %JoinFKPK(Kurir,deleted," = "," AND") */
        Kurir.IdVozilo = deleted.IdVozilo
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Vozilo update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Vozilo  Voznja on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Vozilo"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_13", FK_COLUMNS="IdVozilo" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdVozilo)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdVozilo = inserted.IdVozilo
        FROM inserted
      UPDATE Voznja
      SET
        /*  %JoinFKPK(Voznja,@ins," = ",",") */
        Voznja.IdVozilo = @insIdVozilo
      FROM Voznja,inserted,deleted
      WHERE
        /*  %JoinFKPK(Voznja,deleted," = "," AND") */
        Voznja.IdVozilo = deleted.IdVozilo
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Vozilo update because more than one row has been affected.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_Voznja ON Voznja FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on Voznja */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* Voznja  PlanVoznje on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00020e6a", PARENT_OWNER="", PARENT_TABLE="Voznja"
    CHILD_OWNER="", CHILD_TABLE="PlanVoznje"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="IdVoznja" */
    IF EXISTS (
      SELECT * FROM deleted,PlanVoznje
      WHERE
        /*  %JoinFKPK(PlanVoznje,deleted," = "," AND") */
        PlanVoznje.IdVoznja = deleted.IdVoznja
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Voznja because PlanVoznje exists.'
      GOTO error
    END

    /* erwin Builtin Trigger */
    /* Voznja  Pokupljeno on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Voznja"
    CHILD_OWNER="", CHILD_TABLE="Pokupljeno"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_17", FK_COLUMNS="IdVoznja" */
    IF EXISTS (
      SELECT * FROM deleted,Pokupljeno
      WHERE
        /*  %JoinFKPK(Pokupljeno,deleted," = "," AND") */
        Pokupljeno.IdVoznja = deleted.IdVoznja
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete Voznja because Pokupljeno exists.'
      GOTO error
    END


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_Voznja ON Voznja FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on Voznja */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdVoznja integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* Voznja  PlanVoznje on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00066be5", PARENT_OWNER="", PARENT_TABLE="Voznja"
    CHILD_OWNER="", CHILD_TABLE="PlanVoznje"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_33", FK_COLUMNS="IdVoznja" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdVoznja)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdVoznja = inserted.IdVoznja
        FROM inserted
      UPDATE PlanVoznje
      SET
        /*  %JoinFKPK(PlanVoznje,@ins," = ",",") */
        PlanVoznje.IdVoznja = @insIdVoznja
      FROM PlanVoznje,inserted,deleted
      WHERE
        /*  %JoinFKPK(PlanVoznje,deleted," = "," AND") */
        PlanVoznje.IdVoznja = deleted.IdVoznja
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Voznja update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Voznja  Pokupljeno on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Voznja"
    CHILD_OWNER="", CHILD_TABLE="Pokupljeno"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_17", FK_COLUMNS="IdVoznja" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdVoznja)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdVoznja = inserted.IdVoznja
        FROM inserted
      UPDATE Pokupljeno
      SET
        /*  %JoinFKPK(Pokupljeno,@ins," = ",",") */
        Pokupljeno.IdVoznja = @insIdVoznja
      FROM Pokupljeno,inserted,deleted
      WHERE
        /*  %JoinFKPK(Pokupljeno,deleted," = "," AND") */
        Pokupljeno.IdVoznja = deleted.IdVoznja
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade Voznja update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Grad  Voznja on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Grad"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_31", FK_COLUMNS="PocetniGrad" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(PocetniGrad)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Grad
        WHERE
          /* %JoinFKPK(inserted,Grad) */
          inserted.PocetniGrad = Grad.IDGrad
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    select @nullcnt = count(*) from inserted where
      inserted.PocetniGrad IS NULL
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Voznja because Grad does not exist.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Kurir  Voznja on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Kurir"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_27", FK_COLUMNS="IdKurir" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdKurir)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Kurir
        WHERE
          /* %JoinFKPK(inserted,Kurir) */
          inserted.IdKurir = Kurir.IdKurir
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update Voznja because Kurir does not exist.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Vozilo  Voznja on child update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Vozilo"
    CHILD_OWNER="", CHILD_TABLE="Voznja"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_13", FK_COLUMNS="IdVozilo" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdVozilo)
  BEGIN
    INSERT Vozilo (IdVozilo)
      SELECT IdVozilo
      FROM   inserted
      WHERE
        /* %NotnullFK(inserted," IS NOT NULL AND") */
        
        NOT EXISTS (
          SELECT * FROM Vozilo
          WHERE
            /* %JoinFKPK(inserted,Vozilo," = "," AND") */
            inserted.IdVozilo = Vozilo.IdVozilo
        )
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go




CREATE TRIGGER tD_WorkingUser ON WorkingUser FOR DELETE AS
/* erwin Builtin Trigger */
/* DELETE trigger on WorkingUser */
BEGIN
  DECLARE  @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)
    /* erwin Builtin Trigger */
    /* WorkingUser  Admin on parent delete cascade */
    /* ERWIN_RELATION:CHECKSUM="00018a10", PARENT_OWNER="", PARENT_TABLE="WorkingUser"
    CHILD_OWNER="", CHILD_TABLE="Admin"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="IdAdmin" */
    DELETE Admin
      FROM Admin,deleted
      WHERE
        /*  %JoinFKPK(Admin,deleted," = "," AND") */
        Admin.IdAdmin = deleted.IdWorkingUser

    /* erwin Builtin Trigger */
    /* WorkingUser  Kurir on parent delete cascade */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="WorkingUser"
    CHILD_OWNER="", CHILD_TABLE="Kurir"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_6", FK_COLUMNS="IdKurir" */
    DELETE Kurir
      FROM Kurir,deleted
      WHERE
        /*  %JoinFKPK(Kurir,deleted," = "," AND") */
        Kurir.IdKurir = deleted.IdWorkingUser


    /* erwin Builtin Trigger */
    RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


CREATE TRIGGER tU_WorkingUser ON WorkingUser FOR UPDATE AS
/* erwin Builtin Trigger */
/* UPDATE trigger on WorkingUser */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insIdWorkingUser integer,
           @errno   int,
           @severity int,
           @state    int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* erwin Builtin Trigger */
  /* WorkingUser  Admin on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00042adb", PARENT_OWNER="", PARENT_TABLE="WorkingUser"
    CHILD_OWNER="", CHILD_TABLE="Admin"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_7", FK_COLUMNS="IdAdmin" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdWorkingUser)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdWorkingUser = inserted.IdWorkingUser
        FROM inserted
      UPDATE Admin
      SET
        /*  %JoinFKPK(Admin,@ins," = ",",") */
        Admin.IdAdmin = @insIdWorkingUser
      FROM Admin,inserted,deleted
      WHERE
        /*  %JoinFKPK(Admin,deleted," = "," AND") */
        Admin.IdAdmin = deleted.IdWorkingUser
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade WorkingUser update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* WorkingUser  Kurir on parent update cascade */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="WorkingUser"
    CHILD_OWNER="", CHILD_TABLE="Kurir"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_6", FK_COLUMNS="IdKurir" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(IdWorkingUser)
  BEGIN
    IF @numrows = 1
    BEGIN
      SELECT @insIdWorkingUser = inserted.IdWorkingUser
        FROM inserted
      UPDATE Kurir
      SET
        /*  %JoinFKPK(Kurir,@ins," = ",",") */
        Kurir.IdKurir = @insIdWorkingUser
      FROM Kurir,inserted,deleted
      WHERE
        /*  %JoinFKPK(Kurir,deleted," = "," AND") */
        Kurir.IdKurir = deleted.IdWorkingUser
    END
    ELSE
    BEGIN
      SELECT @errno = 30006,
             @errmsg = 'Cannot cascade WorkingUser update because more than one row has been affected.'
      GOTO error
    END
  END

  /* erwin Builtin Trigger */
  /* Korisnik  WorkingUser on child update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="Korisnik"
    CHILD_OWNER="", CHILD_TABLE="WorkingUser"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="R_5", FK_COLUMNS="IdWorkingUser" */
  IF
    /* %ChildFK(" OR",UPDATE) */
    UPDATE(IdWorkingUser)
  BEGIN
    SELECT @nullcnt = 0
    SELECT @validcnt = count(*)
      FROM inserted,Korisnik
        WHERE
          /* %JoinFKPK(inserted,Korisnik) */
          inserted.IdWorkingUser = Korisnik.IdKorisnik
    /* %NotnullFK(inserted," IS NULL","select @nullcnt = count(*) from inserted where"," AND") */
    
    IF @validcnt + @nullcnt != @numrows
    BEGIN
      SELECT @errno  = 30007,
             @errmsg = 'Cannot update WorkingUser because Korisnik does not exist.'
      GOTO error
    END
  END


  /* erwin Builtin Trigger */
  RETURN
error:
   RAISERROR (@errmsg, -- Message text.
              @severity, -- Severity (0~25).
              @state) -- State (0~255).
    rollback transaction
END

go


