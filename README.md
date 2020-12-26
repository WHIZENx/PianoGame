# PianoGame ![Icon](https://www.img.in.th/images/b2356fa7bf59130fbf79112521061740.png "Icon")
### *204211 [Object-Oriented Programming] Project Chiang Mai University Computer Science 2020*

PianoGame เป็นโปรเจคที่กลุ่มนักศึกษามหาวิทยาลัยเชียงใหม่ชั้นปีที่ 2 ปีการศึกษา 2561 ทำขึ้นมาใน รายวิชา 204211 [Object-Oriented Programming]
เป็นเกมแนว Endless-Run โดยจะให้กดพื้นสีดำ รูปแบบเกมคล้ายๆ เกม Piano โดยจะมีพื้นสีหลายสี แต่ละสีก็จะมีเอ็ฟเฟ็คแตกต่างกันไป

## Installation
> แอพนี้รองรับเฉพาะระบบปฏิบัติการ Android version 4.4 (API 19) ขึ้นไป
> - สามารถ download ได้ตรง Releases Application!

## Interfaces
- Login Interface (Facebook login only!)

![Login](https://www.img.in.th/images/ec4aedb82ca13a4af12ea7aed876a5ea.png "Login")

- Main Interface

![Main](https://www.img.in.th/images/63dc1adfce50f9d64cf318d41d06a041.jpg "Main")

- Shop Interface
> ใช้เหรียญที่ได้จากการเล่น มาซื้อของได้ตามเมนูในร้านค้า

![Shop1](https://www.img.in.th/images/3fea6b7d0627ece33dfaa01129f603ba.png "Shop1")  ![Shop2](https://www.img.in.th/images/66c935a032b292ebde9e08bd0383272a.png "Shop2") ![Shop3](https://www.img.in.th/images/f84cf42427a5fd41822bf5a1d094ab2d.png "Shop3") ![Shop4](https://www.img.in.th/images/af5ab08d5053351389e84b58f161c4a8.png "Shop4")

- Main Game Interface
> ก่อนเข้าสู่เกมจะมีให้เลือกโหมดดังนี้
> 1. Classic Mode: โหมดกดมั่วไป นับคะแนนจากการกด
> > - สีของพื้นจะมีเอ็ฟเฟ็คดังนี้
> > 1. สีแดง: กดโดนแล้วตายทันที
> > 2. สีเหลือง: คะแนนเพิ่มทันที 10 แต้ม
> > 3. สีน้ำเงิน: ความเร็วของเกมจะลดลง 30% มีเวลาประมาณ 5 วินาที
> > 4. สีเขียว: เข้าสู่โหมดเทพ สามารถกดค้างบนหน้าจอ แทนการกดทีละครั้งได้ มีเวลาประมาณ 5 วินาที

![Game](https://www.img.in.th/images/8b81f699c64d6924e362c4075cd5f2eb.png "Game")

> > - เมื่อกดไม่โดนพื้นสี จะ Game over โดยถ้าคุณมีหัวใจคุณสามารถเลือกว่าจะไปต่อหรือหยุดแค่นี้

![Over](https://www.img.in.th/images/b7e132dbae39e0b3d24eebf07e0472b4.png "Over")

> 2. Recode Mode: โหมดนับคะแนนจากเวลาเอาชีวิตรอด

![Game](https://www.img.in.th/images/f5e49b326b7c8e07472e44e05ac5f144.png "Game")

> > - เมื่อกดไม่โดนพื้นสี จะ Game over ทันที

![Over](https://www.img.in.th/images/ef23e61bcb2ca1fb710a2e763eaaa258.png "Over")

- Thanks Team project :)

![Thank](https://www.img.in.th/images/9af51435785c6e128e473f77c5ae7325.png "Thank")

## อยากจะพัฒนาเกมต่อ?
> ควรทำความเข้าใจการ clone ผ่านทาง android studio เพื่อง่ายต่อการดึงข้อมูลมาพัฒนาต่อ
- แอพของเรา สามารถนำไปเขียนต่อเพื่อพัฒนาได้ทุกๆคน
 
  **URL for clone**
  ```
  https://github.com/WHIZENx/PianoGame
  ```
  **เปลี่ยนระบบ account Firebase**
  1. ให้ไปที่ไฟล์ PianoGame\app\
  2. ดาวน์โหลด google-services.json ทาง Firebase ของคุณมาทับได้เลย
