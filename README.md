# LuxuryStaff
All-in-one staff plugin

## Commands

- /staffchat
  - Allows staff members to communicate with each other without other players seeing
  - Aliases: /sc
  - Permission: staff.command.staffchat
  - Permission to see staffchats: staff.staffchat.view

- /report <player> <reason>
  - Allows players to report other players for staff to review
- /reports
  - Allows staff members to review all reports from a GUI
  - Permission: staff.command.reports.view
- /config reload
  - Allows staff to reload all .yml configuration files
  - Permission: staff.command.config.reload
- /authenticate
  - Allows staff members to secure their accounts with a password
  - Requires require-authentication in config to be set to true
  - Aliases: /auth
  - Permission: staff.authentication
  - Anybody with the above permission will be required to have a password and log in upon each join
- /vanish
  - Command to allow staff to vanish from all players without staff.see.vanished
  - Aliases: /v
  - Permissions: staff.command.vanish

