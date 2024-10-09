package kpan.not_enough_oxygen.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.EnumUtils;

public class MyNBTUtil {

    public static boolean hasKey(NBTTagCompound compound, String key, EnumNBTTagType type) {
        switch (type) {
            case BYTE:
            case BYTE_ARRAY:
            case COMPOUND:
            case DOUBLE:
            case END:
            case FLOAT:
            case INT:
            case INT_ARRAY:
            case LIST:
            case LONG:
            case LONG_ARRAY:
            case NUMBER:
            case SHORT:
            case STRING:
                return compound.hasKey(key, type.getId());
            default:
                return false;
        }
    }
    public static boolean hasKeyCompound(NBTTagCompound compound, String key) {
        return compound.hasKey(key, EnumNBTTagType.COMPOUND.getId());
    }
    public static boolean hasKeyNumber(NBTTagCompound compound, String key) {
        return compound.hasKey(key, EnumNBTTagType.NUMBER.getId());
    }

    public static boolean isNumberTag(NBTBase nbt) {
        byte i = nbt.getId();
        return i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6;
    }

    public static boolean canAddTagToList(NBTTagList list, NBTBase nbt) {
        if (nbt.getId() == 0)
            return false;

        if (list.getTagType() == 0) {
            return true;
        } else {
            return list.getTagType() == nbt.getId();
        }
    }

    public static long[] getLongArray(NBTTagLongArray nbt) {
        return ReflectionUtil.getObfPrivateValue(NBTTagLongArray.class, nbt, "data", "field_193587_b");
    }

    public static NBTTagCompound createVec3dTag(Vec3d vec) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setDouble("x", vec.x);
        nbttagcompound.setDouble("y", vec.y);
        nbttagcompound.setDouble("z", vec.z);
        return nbttagcompound;
    }
    public static Vec3d getVec3dFromTag(NBTTagCompound tag) {
        return new Vec3d(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"));
    }
    public static NBTBase createAABBTag(@Nullable AxisAlignedBB aabb) {
        if (aabb != Block.NULL_AABB) {
            NBTTagList taglist = new NBTTagList();
            taglist.appendTag(new NBTTagDouble(aabb.minX));
            taglist.appendTag(new NBTTagDouble(aabb.minY));
            taglist.appendTag(new NBTTagDouble(aabb.minZ));
            taglist.appendTag(new NBTTagDouble(aabb.maxX));
            taglist.appendTag(new NBTTagDouble(aabb.maxY));
            taglist.appendTag(new NBTTagDouble(aabb.maxZ));
            return taglist;
        } else {
            return new NBTTagString("null");
        }
    }
    @Nullable
    public static AxisAlignedBB getAABBformTag(NBTBase nbt, AxisAlignedBB defaultValue) {
        if (nbt instanceof NBTTagList) {
            NBTTagList list = (NBTTagList) nbt;
            if (list.tagCount() >= 6)
                return new AxisAlignedBB(list.getDoubleAt(0), list.getDoubleAt(1), list.getDoubleAt(2), list.getDoubleAt(3), list.getDoubleAt(4), list.getDoubleAt(5));
        }
        if (nbt instanceof NBTTagString) {
            if (((NBTTagString) nbt).getString().equals("null"))
                return Block.NULL_AABB;
        }
        return defaultValue;
    }
    @Nullable
    public static UUID getUUIDFromTag(NBTTagCompound tag) {
        if (hasKey(tag, "M", EnumNBTTagType.LONG) && hasKey(tag, "L", EnumNBTTagType.LONG))
            return new UUID(tag.getLong("M"), tag.getLong("L"));
        else
            return null;
    }

    public static NBTTagCompound writeNull(NBTTagCompound compound, String name) {
        compound.setTag(name, new NBTTagEnd());
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, boolean value) {
        compound.setBoolean(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, byte value) {
        compound.setByte(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, short value) {
        compound.setShort(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, int value) {
        compound.setInteger(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, long value) {
        compound.setLong(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, float value) {
        compound.setFloat(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, double value) {
        compound.setDouble(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, byte[] value) {
        compound.setByteArray(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, @Nullable String value) {
        if (value != null)
            compound.setString(name, value);
        else
            writeNull(compound, name);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, @Nullable List<?> value) {
        if (value != null)
            compound.setTag(name, toNBT(value));
        else
            writeNull(compound, name);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, int[] value) {
        compound.setIntArray(name, value);
        return compound;
    }
    public static NBTTagCompound write(NBTTagCompound compound, String name, long[] value) {
        compound.setTag(name, new NBTTagLongArray(value));
        return compound;
    }
    public static <T extends Enum<T>> NBTTagCompound write(NBTTagCompound compound, String name, Enum<T> value) { return write(compound, name, value.name()); }
    public static NBTTagCompound writeAABB(NBTTagCompound compound, String name, @Nullable AxisAlignedBB aabb) {
        compound.setTag(name, createAABBTag(aabb));
        return compound;
    }

    public static Boolean readNumberBool(NBTTagCompound compound, String name) {
        if (hasKeyNumber(compound, name))
            return compound.getBoolean(name);
        else
            return null;
    }
    public static boolean readNumberBool(NBTTagCompound compound, String name, boolean defaultValue) {
        if (hasKeyNumber(compound, name))
            return compound.getBoolean(name);
        else
            return defaultValue;
    }
    @Nullable
    public static Integer readNumberInt(NBTTagCompound compound, String name) {
        if (hasKeyNumber(compound, name))
            return compound.getInteger(name);
        else
            return null;
    }
    public static int readNumberInt(NBTTagCompound compound, String name, int defaultValue) {
        if (hasKeyNumber(compound, name))
            return compound.getInteger(name);
        else
            return defaultValue;
    }
    @Nullable
    public static Double readNumberDouble(NBTTagCompound compound, String name) {
        if (hasKeyNumber(compound, name))
            return compound.getDouble(name);
        else
            return null;
    }
    public static double readNumberDouble(NBTTagCompound compound, String name, double defaultValue) {
        if (hasKeyNumber(compound, name))
            return compound.getDouble(name);
        else
            return defaultValue;
    }
    @Nullable
    public static String readString(NBTTagCompound compound, String name) {
        return readString(compound, name, null);
    }
    public static String readString(NBTTagCompound compound, String name, String defaultValue) {
        if (hasKey(compound, name, EnumNBTTagType.STRING))
            return compound.getString(name);
        else
            return defaultValue;
    }
    @Nullable
    public static <T extends Enum<T>> T readEnum(NBTTagCompound compound, String name, Class<T> enumType) {
        return readEnum(compound, name, enumType, null);
    }
    public static <T extends Enum<T>> T readEnum(NBTTagCompound compound, String name, Class<T> enumType, T defaultValue) {
        String value = compound.getString(name);
        if (value.isEmpty())
            return defaultValue;
        return EnumUtils.getEnum(enumType, value, defaultValue);
    }
    @Nullable
    public static AxisAlignedBB readAABB(NBTTagCompound compound, String name, AxisAlignedBB defaultValue) {
        NBTBase nbt = compound.getTag(name);
        return getAABBformTag(nbt, defaultValue);
    }

    public static NBTBase toNBT(@Nullable Object object) {
        if (object instanceof Boolean)
            return new NBTTagByte((byte) ((boolean) object ? 1 : 0));
        else if (object instanceof Byte)
            return new NBTTagByte((byte) object);
        else if (object instanceof Short)
            return new NBTTagShort((short) object);
        else if (object instanceof Integer)
            return new NBTTagInt((int) object);
        else if (object instanceof Long)
            return new NBTTagLong((long) object);
        else if (object instanceof Float)
            return new NBTTagFloat((float) object);
        else if (object instanceof Double)
            return new NBTTagDouble((double) object);
        else if (object instanceof String)
            return new NBTTagString((String) object);
        else if (object instanceof byte[])
            return new NBTTagByteArray((byte[]) object);
        else if (object instanceof int[])
            return new NBTTagIntArray((int[]) object);
        else if (object instanceof long[])
            return new NBTTagLongArray((long[]) object);
        else if (object instanceof List<?> list) {
            NBTTagList taglist = new NBTTagList();
            for (Object item : list) {
                taglist.appendTag(toNBT(item));
            }
            return taglist;
        } else if (object instanceof Map<?, ?> map) {
            NBTTagCompound compound = new NBTTagCompound();
            for (Entry<?, ?> entry : map.entrySet()) {
                String name = entry.getKey().toString();
                compound.setTag(name, toNBT(entry.getValue()));
            }
            return compound;
        }
        throw new IllegalArgumentException(object + "はNBTに変換できません");
    }
    @Nullable
    public static Object fromNBT(@Nullable NBTBase nbt) {
        if (nbt == null)
            return null;
        switch (nbt.getId()) {
            case 0:// end
                throw new IllegalArgumentException("ENDはオブジェクトに変換できません");
            case 1:// byte
                return ((NBTTagByte) nbt).getByte();
            case 2:// short
                return ((NBTTagShort) nbt).getShort();
            case 3:// int
                return ((NBTTagInt) nbt).getInt();
            case 4:// long
                return ((NBTTagLong) nbt).getLong();
            case 5:// float
                return ((NBTTagFloat) nbt).getFloat();
            case 6:// double
                return ((NBTTagDouble) nbt).getDouble();
            case 7:// byte[]
                return ((NBTTagByteArray) nbt).getByteArray();
            case 8:// string
                return ((NBTTagString) nbt).getString();
            case 9:// list
            {
                NBTTagList taglist = (NBTTagList) nbt;
                List<Object> list = new ArrayList<>();
                for (NBTBase item : taglist) {
                    list.add(fromNBT(item));
                }
                return list;
            }
            case 10:// compound
            {
                NBTTagCompound compound = (NBTTagCompound) nbt;
                Map<String, Object> map = new HashMap<>();
                for (String key : compound.getKeySet()) {
                    map.put(key, fromNBT(compound.getTag(key)));
                }
                return map;
            }
            case 11:// int[]
                return ((NBTTagIntArray) nbt).getIntArray();
            case 12:// long[]
                return getLongArray((NBTTagLongArray) nbt);
            default:
                throw new IllegalArgumentException("不明なNBT:" + nbt.toString());
        }
    }
    public static NBTTagCompound toNBT(Map<String, ?> map) { return (NBTTagCompound) toNBT((Object) map); }
    @SuppressWarnings("unchecked")
    public static Map<String, ?> toNBT(NBTTagCompound compound) { return (Map<String, ?>) fromNBT(compound); }

    public static void readListString(NBTTagList nbt, List<String> list) {
        for (int i = 0; i < nbt.tagCount(); i++) {
            list.add(nbt.getStringTagAt(i));
        }
    }
    public static void readIntMap(NBTTagCompound compound, Map<String, Integer> map) {
        for (String key : compound.getKeySet()) {
            map.put(key, compound.getInteger(key));
        }
    }
    public static void readDoubleMap(NBTTagCompound compound, Map<String, Double> map) {
        for (String key : compound.getKeySet()) {
            map.put(key, compound.getDouble(key));
        }
    }

    public static void writeToTileEntity(TileEntity tileEntity, NBTTagCompound nbt) {
        BlockPos pos = tileEntity.getPos();
        NBTTagCompound compound = nbt.copy();
        compound.setInteger("x", pos.getX());
        compound.setInteger("y", pos.getY());
        compound.setInteger("z", pos.getZ());
        tileEntity.readFromNBT(compound);
        tileEntity.markDirty();
    }

    public enum EnumNBTTagType {
        END(Constants.NBT.TAG_END),
        BYTE(Constants.NBT.TAG_BYTE),
        SHORT(Constants.NBT.TAG_SHORT),
        INT(Constants.NBT.TAG_INT),
        LONG(Constants.NBT.TAG_LONG),
        FLOAT(Constants.NBT.TAG_FLOAT),
        DOUBLE(Constants.NBT.TAG_DOUBLE),
        BYTE_ARRAY(Constants.NBT.TAG_BYTE_ARRAY),
        STRING(Constants.NBT.TAG_STRING),
        LIST(Constants.NBT.TAG_LIST),
        COMPOUND(Constants.NBT.TAG_COMPOUND),
        INT_ARRAY(Constants.NBT.TAG_INT_ARRAY),
        LONG_ARRAY(Constants.NBT.TAG_LONG_ARRAY),
        NUMBER(Constants.NBT.TAG_ANY_NUMERIC),
        ;

        private final int id;

        private EnumNBTTagType(int id) {
            this.id = id;
        }
        public int getId() { return id; }
    }
}
