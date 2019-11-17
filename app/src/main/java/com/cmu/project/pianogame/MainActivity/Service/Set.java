package com.cmu.project.pianogame.MainActivity.Service;

import com.cmu.project.pianogame.Database.Coin;
import com.cmu.project.pianogame.Database.ColorBox;
import com.cmu.project.pianogame.Database.Energy;
import com.cmu.project.pianogame.Database.Love;
import com.cmu.project.pianogame.Database.Music;
import com.cmu.project.pianogame.Database.Score;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;

public class Set {

    private static DatabaseReference reference;

    public static void CreateEnergy(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Energy").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", id);
                    hashMap.put("energy", 30);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void CreateCoin(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Coins").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", id);
                    hashMap.put("coin", 0);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void CreateTheme(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Themes").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", id);
                    hashMap.put("cur_theme", "bg");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void CreateMusic(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Musics").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", id);
                    hashMap.put("cur_music", "alone");
                    hashMap.put("cur_name_music", "Alone");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void CreateColor(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Colors").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", id);
                    hashMap.put("colorbox", "#000000");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void CreateScore(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Scores").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", Firebase.getCurrent().getUid());
                    hashMap.put("maxscore", 0);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void CreateLove(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Loves").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", Firebase.getCurrent().getUid());
                    hashMap.put("love", 0);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static int coins;
    public static void getCoin(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Coins").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Coin coin = dataSnapshot.getValue(Coin.class);
                coins = coin.getCoin();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static String colors;
    public static void getColor(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Colors").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ColorBox colorBox = dataSnapshot.getValue(ColorBox.class);
                colors = colorBox.getColorbox();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static int loves;
    public static void getLove(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Loves").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Love love = dataSnapshot.getValue(Love.class);
                loves = love.getLove();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static int energys;
    public static void getEnergy(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Energy").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Energy energy = dataSnapshot.getValue(Energy.class);
                energys = energy.getEnergy();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static String musics;
    public static String name_musics;
    public static void getMusic(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Musics").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Music music = dataSnapshot.getValue(Music.class);
                musics = music.getCur_music();
                name_musics = music.getCur_name_music();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static int max_score_mode;
    public static void getMaxScore(final String id) {
        reference = FirebaseDatabase.getInstance().getReference("Scores").child("ScoreMode").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Score score = dataSnapshot.getValue(Score.class);
                    max_score_mode = score.getMaxscore();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void SetLove(final String id, final int deleteLove) {
        reference = FirebaseDatabase.getInstance().getReference("Loves").child(id).child("love");
        reference.setValue(deleteLove);
    }

    public static void SetColor(final String id, final String getTag) {
        reference = FirebaseDatabase.getInstance().getReference("Colors").child(id).child("colorbox");
        reference.setValue(getTag);
    }

    public static void SetMusic(final String id, final String getTag) {
        reference = FirebaseDatabase.getInstance().getReference("Musics").child(id).child("cur_music");
        reference.setValue(getTag);
    }

    public static void SetNameMusic(final String id, final String getTag) {
        reference = FirebaseDatabase.getInstance().getReference("Musics").child(id).child("cur_name_music");
        reference.setValue(getTag);
    }

    public static void SetTheme(final String id, final String getTag) {
        reference = FirebaseDatabase.getInstance().getReference("Themes").child(id).child("cur_theme");
        reference.setValue(getTag);
    }

    public static void SetCoin(final String id, final int deleteCoin) {
        reference = FirebaseDatabase.getInstance().getReference("Coins").child(id).child("coin");
        reference.setValue(deleteCoin);
    }

    public static void SetEnergy(final String id, final int deleteEnergy) {
        reference = FirebaseDatabase.getInstance().getReference("Energy").child(id).child("energy");
        reference.setValue(deleteEnergy);
    }

    public static void DecreaseEnergy(final String id, final int getEnergy) {
        reference = FirebaseDatabase.getInstance().getReference("Energy").child(id).child("energy");
        reference.setValue(getEnergy-1);
    }

    public static void IncreaseEnergy(final String id, final int getEnergy) {
        reference = FirebaseDatabase.getInstance().getReference("Energy").child(id).child("energy");
        reference.setValue(getEnergy+1);
    }
}
