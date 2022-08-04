package com.example.TurkcellAtmProject.atm.sql.dto;

import java.util.Scanner;

import com.example.TurkcellAtmProject.atm.IAtm;
import com.example.TurkcellAtmProject.atm.sql.dao.RegisterDao;

public class AtmDto implements IAtm {
	
	// object variable
	RegisterDto loginDto;
	SafeDto kasa;
	Scanner klavye;
	RegisterDto registerDto;
	RegisterDao registerDao;
	RegisterDto registerDto2;
	
	// parametresiz constructor
	public AtmDto() {
		loginDto = new RegisterDto();
		kasa = new SafeDto();
		klavye = new Scanner(System.in);
		registerDto = new RegisterDto();
		registerDao = new RegisterDao();
		registerDto2 = new RegisterDto();
	}
	
	// database login bilgisi
	@Override
	public boolean bankGiris() {
		// kullanıcıdan alacağım password
		String userPassword;
		
		// login giriş hakkı 3
		int counter = 3;
		
		// kullanıcı doğru girene kadar sisteme giriş yapılacak 3 hakkımız bulunuyor.
		while (counter > 0) {
			System.out.println("Lütfen Şifreyi Giriniz");
			userPassword = klavye.nextLine();
			registerDto.setRegisterPassword(userPassword);
			registerDto2 = registerDao.isThereUser(registerDto);
			
			if (registerDto2 != null) {
				if (registerDto2.getRegisterPassword().equals(userPassword)) {
					System.out.println("Bank Seçime Yönlendiriliyorsunuz");
					secim();
					return true;
				}
			}
			counter--;
			System.out.println("Kalan hakkınız: " + counter);
			if (counter <= 0) {
				System.out.println("Kartınız bloke oldu: Müşteri Hizmetlerini aramamı ister misiniz\n çıkış yapılıyor");
				System.exit(0);
			}
		}
		return false;
	}
	
	@Override
	public void secim() {
		while (true) {
			System.out.println("\nSeçim yapınız");
			System.out.println("1-)Ozet\n2-)Para Ekle\n3-)Para Cek\n4-)EFT\n5-)HAVALE\n6-)Cikis");
			Scanner klavye = new Scanner(System.in);
			int chooise = klavye.nextInt();
			
			switch (chooise) {
				case 1:
					ozet();
					break;
				
				case 2:
					paraEkle();
					break;
				
				case 3:
					paraCek();
					break;
				case 4:
					eft();
					break;
				case 5:
					havale();
					break;
				
				case 6:
					System.out.println("Atm Çıkış Yapılıyor\n devam etmek icin bir tusa basiniz");
					klavye.hasNext();
					AvmDto avmDto = new AvmDto();
					avmDto.avmMain();
					break;
				
				default:
					System.out.println("Doğru seçim yapmadınız");
					// throw new IllegalArgumentException("Unexpected value: ");
					break;
			}
		}
	}
	
	// HAVALE
	private void havale() {
		System.out.println("**********Havale İşlemleri***********");
		HavaleDto havaleDto = new HavaleDto();
		System.out.println("Havale Adını giriniz");
		havaleDto.setHavaleName(klavye.nextLine());
		System.out.println("Havale Miktarını giriniz");
		double amout = klavye.nextDouble();
		havaleDto.setHavaleAmount(amout);
		System.out.println("Yapılan Havale adı: " + havaleDto.getHavaleName());
		System.out.println("Yapılan Havale Miktarı: " + havaleDto.getHavaleAmount());
		kasa.amount -= havaleDto.getHavaleAmount();
		System.out.println("Kalan Miktarınız: " + kasa.amount);
	}
	
	// EFT
	private void eft() {
		System.out.println("**********Eft İşlemleri***********");
		EftDto eftDto = new EftDto();
		System.out.println("Eft Adını giriniz");
		eftDto.setEftName(klavye.nextLine());
		System.out.println("Eft Miktarını giriniz");
		double amout = klavye.nextDouble();
		eftDto.setEftAmount(amout);
		System.out.println("Yapılan Eft adı: " + eftDto.getEftName());
		System.out.println("Yapılan Eft Miktarı: " + eftDto.getEftAmount());
		kasa.amount -= eftDto.getEftAmount();
		System.out.println("Kalan Miktarınız: " + kasa.amount);
	}
	
	@Override
	public void kasa() {
		// SafeDto kasa = new SafeDto();
		
	}
	
	@Override
	public void ozet() {
		System.out.println("Ozet");
		System.out.println("Bakiyeniz: " + kasa.getAmount());
	}
	
	@Override
	public void paraEkle() {
		Scanner klavye = new Scanner(System.in);
		System.out.println("Eklenecek Para miktarı yazınız");
		double addMoney = klavye.nextDouble();
		kasa.amount += addMoney;
		System.out.println("Bakiyeniz: " + kasa.getAmount());
		
	}
	
	@Override
	public void paraCek() {
		Scanner klavye = new Scanner(System.in);
		System.out.println("Para Çekilecek miktarı yazınız");
		double reduceMoney = klavye.nextDouble();
		
		// ek hesaptan en fazla -1000 kadar gidebilir.
		if (kasa.amount <= 0) {
			System.out.println("Öncelikle Para eklemelisiniz");
		} else if (reduceMoney >= 5000) {
			System.out.println(
					" çekeceğiniz miktar " + reduceMoney + "ancak Tek seferde 5000 TL kadar  Para çekebilirsiniz");
		} else {
			kasa.amount -= reduceMoney;
			System.out.println("Bakiyeniz: " + kasa.getAmount());
		}
		
	}
	
	public static void main(String[] args) {
		AtmDto atmDto = new AtmDto();
		atmDto.bankGiris();
	}
	
}