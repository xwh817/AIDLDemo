package xwh.demo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xwh on 2019/8/9.
 */
public class Person implements Parcelable {
	private String name;
	private int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	protected Person(Parcel in) {
		name = in.readString();
		age = in.readInt();
	}

	public void setName(String name) {
		this.name = name;
	}

	public static final Creator<Person> CREATOR = new Creator<Person>() {
		@Override
		public Person createFromParcel(Parcel in) {
			return new Person(in);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(age);
	}

	@Override
	public String toString() {
		return "Person:"+this.hashCode()+"{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
