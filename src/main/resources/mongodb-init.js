db = db.getSiblingDB('nowplaying');


db.song_reviews.drop();


db.createCollection('song_reviews');

db.song_reviews.createIndex({ "song_id": 1 });
db.song_reviews.createIndex({ "artist": 1 });
db.song_reviews.createIndex({ "tags": 1 });


db.song_reviews.insertMany([
    {
        song_id: 1,
        song_title: "Blinding Lights",
        artist: "The Weeknd",
        tags: ["pop", "synthwave", "upbeat"],
        reviews: [
            {
                user_id: 1,
                username: "musicfan99",
                rating: 5,
                comment: "Absolute classic! Loved it.",
                date: "2026-05-10",
                helpful_count: 147,
                replies: [
                    {
                        user_id: 2,
                        username: "beatslover",
                        comment: "Totally agree!",
                        date: "2026-05-11"
                    }
                ]
            }
        ],
        play_history: [
            {
                user_id: 1,
                username: "musicfan99",
                played_at: "2026-05-10T14:30:00Z",
                duration_listened_sec: 200,
                completed: true
            },
            {
                user_id: 2,
                username: "beatslover",
                played_at: "2026-05-15T09:15:00Z",
                duration_listened_sec: 200,
                completed: true
            }
        ],
        total_plays: 2,
        average_rating: 5.0,
        created_at: new Date("2026-05-10"),
        updated_at: new Date("2026-05-15")
    },
    {
        song_id: 3,
        song_title: "Shake It Off",
        artist: "Taylor Swift",
        tags: ["pop", "upbeat", "dance"],
        reviews: [
            {
                user_id: 3,
                username: "pop_culture",
                rating: 5,
                comment: "Such a fun and energetic song!",
                date: "2026-05-08",
                helpful_count: 312,
                replies: []
            }
        ],
        play_history: [
            {
                user_id: 3,
                username: "pop_culture",
                played_at: "2026-05-08T15:45:00Z",
                duration_listened_sec: 242,
                completed: true
            },
            {
                user_id: 1,
                username: "musicfan99",
                played_at: "2026-05-20T12:00:00Z",
                duration_listened_sec: 242,
                completed: true
            }
        ],
        total_plays: 2,
        average_rating: 5.0,
        created_at: new Date("2026-05-08"),
        updated_at: new Date("2026-05-20")
    },
    {
        song_id: 5,
        song_title: "Bad Guy",
        artist: "Billie Eilish",
        tags: ["alternative", "electronica", "dark"],
        reviews: [
            {
                user_id: 2,
                username: "beatslover",
                rating: 4,
                comment: "Great production quality.",
                date: "2026-05-15",
                helpful_count: 89,
                replies: []
            }
        ],
        play_history: [
            {
                user_id: 2,
                username: "beatslover",
                played_at: "2026-05-15T21:00:00Z",
                duration_listened_sec: 194,
                completed: true
            },
            {
                user_id: 1,
                username: "musicfan99",
                played_at: "2026-05-20T17:30:00Z",
                duration_listened_sec: 194,
                completed: true
            }
        ],
        total_plays: 2,
        average_rating: 4.0,
        created_at: new Date("2026-05-15"),
        updated_at: new Date("2026-05-20")
    }
]);


print("MongoDB Initialization Complete!");
print("Song Reviews Collection Created with " + db.song_reviews.countDocuments() + " documents");
print("\nCollection Indexes:");
db.song_reviews.getIndexes().forEach(function(index) {
    print("  - " + JSON.stringify(index));
});
