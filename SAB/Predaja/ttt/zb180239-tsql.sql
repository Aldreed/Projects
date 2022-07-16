


create trigger [TR_TransportOffer] on Paket for insert as
begin
	Declare @IdPaketa int
	Declare @ToAdr int
	Declare @FromAdr int
	Declare @Tezina Decimal(10,3)
	Declare @Tip int
	Declare @Cena Decimal(10,3)
	Declare @Razdaljina Decimal(10,3)
	Declare @CenaKG Decimal(10,3)
	Declare @XCord int
	Declare @YCord int
	Declare @XCord1 int
	Declare @YCord1 int
	Declare @MyCursor Cursor
	Declare @CordsCursor Cursor
	Declare @CordsCursor1 Cursor

	


	set @MyCursor = Cursor for select ToAdresa,FromAdresa,TezinaPaketa,TipPaketa,IdPaket from inserted
	open @MyCursor
	fetch next from @MyCursor into @ToAdr,@FromAdr,@Tezina,@Tip,@IdPaketa
	

	while @@FETCH_STATUS = 0
	begin
		
		set @CordsCursor = Cursor for select XCord,YCord from Adresa where IdAdresa = @FromAdr;
		set @CordsCursor1 = Cursor for select XCord,YCord from Adresa where IdAdresa = @ToAdr;					

		open @CordsCursor
		open @CordsCursor1

		fetch next from @CordsCursor into @XCord,@YCord;
		fetch next from @CordsCursor1 into @XCord1,@YCord1;

		set @Razdaljina =  SQRT(SQUARE(@XCord - @XCord1) + SQUARE(@YCord - @YCord1))


		set @Cena = case 
		when @Tip =0 then (115 + @Tezina * 0)*@Razdaljina
		when @Tip =1 then (175 + @Tezina * 100)*@Razdaljina
		when @Tip =2 then (250 + @Tezina * 100)*@Razdaljina
		when @Tip =3 then (350 + @Tezina * 500)*@Razdaljina
		end
		
		--select @Cena as Cena;

		update Paket set Cena = @Cena, TipPaketa = @Tip, TezinaPaketa = @Tezina, StatusPaketa = 0, VremeKreiranjaZahteva = GETDATE(), 
		VremePrihvatanjaPonude = NULL, ToAdresa = @ToAdr, FromAdresa = @FromAdr where IdPaket = @IdPaketa

		close @CordsCursor;
		close @CordsCursor1;

		deallocate @CordsCursor;
		deallocate @CordsCursor1;
		fetch next from @MyCursor into @ToAdr,@FromAdr,@Tezina,@Tip,@IdPaketa
	end

	close @MyCursor;
	deallocate @MyCursor;
	

end;


