package br.com.apps.churrascow.model

enum class ActionSummary(
    val description: String
) {

    EVENT_INSERT("Evento adicionado"),
    EVENT_REVENUE_SET("Event Revenue Set"),

    GUEST_INSERT("Guest Inserted"),
    GUEST_REMOVED("Guest Removed"),
    GUEST_PAID("Guest Paid"),

    EXPENSE_INSERT("Expense Inserted"),
    EXPENSE_REMOVED("Expense Removed"),

    TRANSFER_INSERT("Expense Inserted"),
    TRANSFER_REMOVED("Expense Removed"),



}