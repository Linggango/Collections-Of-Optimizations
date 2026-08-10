package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.item.Item;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class PatchouliBookLookup {

    private static final Map<Item, Book> CACHE = new HashMap<>();

    private static int knownBookCount = -1;

    private PatchouliBookLookup() {
    }

    @Nullable
    public static synchronized Book forItem(Item item) {
        Map<net.minecraft.resources.ResourceLocation, Book> books = BookRegistry.INSTANCE.books;
        int count = books.size();
        if (count != knownBookCount) {
            CACHE.clear();
            knownBookCount = count;
        } else if (CACHE.containsKey(item)) {
            return CACHE.get(item);
        }

        Book match = null;
        for (Book book : books.values()) {
            if (book.getBookItem().is(item)) {
                match = book;
                break;
            }
        }
        CACHE.put(item, match);
        return match;
    }
}
