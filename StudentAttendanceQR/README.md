# Student Attendance QR (Android, Kotlin)

Features:
- Import students from Excel (.xlsx): columns `Name | LRN | ParentName | ParentPhone`
- Generate QR codes for each student (based on LRN)
- Scan QR for Arrival/Dismissal, automatically send SMS to parent with time
- Teacher profile (name, grade & section, school year, dismissal time)
- Editable SMS templates with placeholders: {NAME}, {TIME}, {DISMISSAL}, {TEACHER}, {GRADE}, {SY}

## Build
- Open in Android Studio (Giraffe+), let Gradle sync.
- Run on Android 7.0+ phone with SIM for SMS.
- App asks for Camera & Send SMS permissions at first launch.

## Notes
- SMS uses device SIM via `SmsManager` (no internet needed).
- Excel parsing uses POI (Android port). If you face memory issues, import a CSV instead and adjust importer.
- QR content is JSON: `{ "lrn": "<LRN>" }`. Keep LRNs private to avoid spoofing.
