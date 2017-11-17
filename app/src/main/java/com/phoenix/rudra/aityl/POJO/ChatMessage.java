/* Sheldon - ChatMessage.java
 *
* Copyright (C) 2017 Rohit Das <rohit.das950@gmail.com>
 *
 * Authors:
 *   Rohit Das <rohit.das950@gmail.com>
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.phoenix.rudra.aityl.POJO;

public class ChatMessage {
    private boolean isImage, isMine;
    private String content;

    public ChatMessage(String message, boolean mine, boolean image)
    {
        content = message;
        isMine = mine;
        isImage = image;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public boolean isMine()
    {
        return isMine;
    }

    public void setMine(boolean isMine)
    {
        this.isMine = isMine;
    }

    public boolean isImage()
    {
        return isImage;
    }

    public void setIsImage(boolean isImage)
    {
        this.isImage = isImage;
    }
}
